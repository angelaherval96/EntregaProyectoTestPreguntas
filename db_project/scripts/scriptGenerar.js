/*ELEMENTOS DEL DOM */
/*SELECTORES DE LOS CONTENEDORES PRINCIPALES*/
let generarContainer = document.querySelector(".generar__container"),
    mostrarContainer = document.querySelector(".mostrar__container"),
    numeroPreguntas = document.querySelector("#numeroPreguntas"),
    numeroRespuestas = document.querySelector("#numeroOpciones");

/*Variables para seleccionar todos los elementos de una clase y crear arrays que me permitan añadir la funcionalidad para que se ejecute la función */
let botones = document.querySelectorAll(".btn"),
    links = document.querySelectorAll(".link"),
    cards = document.querySelectorAll(".card"),
    asignaturaLista = document.querySelectorAll(".asignatura"),
    titulosAsignaturas = document.querySelectorAll(".asignatura__title"),
    temaAsignatura = document.querySelectorAll(".asignatura__contenido"),
    inputs = document.querySelectorAll(".input"),
    btnsEnviar = document.querySelectorAll(".btnEnviar"),
    btnsEditar = document.querySelectorAll(".btnEditar"),
    niveles = document.querySelectorAll(".nivel"),
    nivelesTemas = document.querySelectorAll(".nivel__contenido"),
    tituloNivel = document.querySelectorAll(".nivel__title");

/*Variables elementos individuales */
let imagenInicio = document.querySelector(".img__container");
let btnGenerar = document.querySelector(".btnGenerar");

/*Variables de control */
let control = true,
    controlField,
    codigo,
    editarTema,
    numOpcionesMostrado = false;

/*Variables para la lógica de generación*/
let temaExamen,
    asignaturaExamen,
    nivelExamen,
    numeroTotalPreguntas,
    numeroTotalRespuestas;

/*Funciones para ocultar/mostrar elementos (general) */
const mostrar = (elemento) => {
    if(elemento.classList.contains("hidden")) {
        elemento.classList.remove("hidden");
    }
}
const ocultar = (elemento) => {
    if(!elemento.classList.contains("hidden")) {
        elemento.classList.add("hidden");
    }
}
//Función para limpiar los input en caso que el usuario introduzca valores que no son válidos
const limpiar = (elementoLimpiar) => {
    elementoLimpiar.value="";
    elementoLimpiar.focus();
}
/*Función para deshabilitar el input una vez que el usuario ha introducido un valor (válido)*/
const deshabilitar = (elementoDeshabilitar) => {
    elementoDeshabilitar.disabled=true;
}
/*Función para habilitar el input en caso que el usuario desee editar el valor del input*/
const habilitar = (elementoHabilitar) => {
    elementoHabilitar.disabled=false;
}
/*Funcion btnEnviar */
const enviarElemento = (input, btnEditar, btnEnviar) => {
    deshabilitar(input);
    mostrar(btnEditar);
    ocultar(btnEnviar);
}
/*Funcion btnEditar */

//Añadir el removeAddListener();
const editarElemento = (input, btnEnviar, btnEditar) => {
    habilitar(input);
    mostrar(btnEnviar);
    ocultar(btnEditar);
}
/*Funciones de los links */
links.forEach((link) => {
    link.addEventListener("click", () => {
        let idLink = link.getAttribute("id");
    
    if(idLink === "mostrar") {
        mostrar(mostrarContainer);
        ocultar(generarContainer);
    } else {
        ocultar(mostrarContainer);
        mostrar(generarContainer);
    }
    ocultar(imagenInicio);
    });
});
/*Funciones de los botones según el grado (Primaria, Secundaria, etc)*/
botones.forEach((btn) => {
    btn.addEventListener("click", () => {
        let idPadre = btn.parentElement.getAttribute("id"),
            elementoSiguiente = document.querySelector(`#${document.querySelector(`#${idPadre}`).parentElement.getAttribute("id")}`);
        
        mostrar(document.querySelector(".niveles__grado"));

        cards.forEach((card) => {
            if(card.getAttribute("id") !== idPadre) {
                ocultar(card); 
            }
        });

        niveles.forEach((nivel) => {
            ocultar(nivel);
            if(nivel.classList.contains(`nivel__${idPadre}`)) {
                mostrar(nivel);
            }
        });
        ocultar(btn);
        siguienteSeccion(elementoSiguiente);
    });
})
/*Función de los grados*/
tituloNivel.forEach((titulo) => {
    titulo.addEventListener("click", () => {
        let siguiente = document.querySelector("#cards");

        /*Con este forEach() le decimos que de la lista de asignaturas (".asignatura") busque aquel el padre del titulo clicado y, en caso de tener hijos, que los muestre */
        asignaturaLista.forEach((asignatura) => {
            if(document.querySelector(`#${titulo.parentElement.getAttribute("id")}`).hasChildNodes()) {
                mostrar(asignatura);
            }
        })
        
        siguienteSeccion(siguiente);
    })
})
/*MOSTRAR CONTENIDO POR CADA ASIGNATURA*/
titulosAsignaturas.forEach((asignatura) => {
    asignatura.addEventListener("click", () => {
        let idPadre = asignatura.parentElement.getAttribute("id"),
            elemento = document.querySelector(`#${idPadre}`).querySelector(".contenido"),
            imgElemento = document.querySelector(`#${idPadre}`).querySelector(".asignatura__img");

        if(control) {
            mostrar(elemento);
            imgElemento.src="icons/down-arrow.svg";
            control=!control;
        } else {
            ocultar(elemento);
            imgElemento.src="icons/left-arrow.svg";
            control=!control;
        }
    });
})
/*SELECCION DE LA CANTIDAD DE PREGUNTAS Y MOSTRAR LA ASIGNATURA JUNTO CON EL TEMA SELECCIONADO */
temaAsignatura.forEach((tema) => {

    tema.addEventListener("click", () => {

        /*Variables para obtener los id y poder ocultar los elementos que no correspondan al tema, asignatura y nivel seleccionado */
        let idPadre = tema.parentElement.getAttribute("id"),
            idAbuelo = document.querySelector(`#${idPadre}`).parentElement.getAttribute("id"),

            elementoSiguiente = document.querySelector("#niveles__grado");

        //Eliminamos la imagen, usamos el elemento abuelo para apuntar a la imagen del contenedor seleccionado
        document.querySelector(`#${idAbuelo}`).querySelector(".asignatura__img").remove();

        //Almacenamos el valor del tema en la variable para generar el examen según el tema seleccionado.
        temaExamen = tema.innerHTML;

        asignaturaExamen = document.querySelector(`#${idPadre}`).previousElementSibling.querySelector(".asignatura__name").innerHTML;

        nivelExamen = document.querySelector(`#${idAbuelo}`).parentElement.querySelector(".nivel__title").innerHTML;
        
        /*Almacenamos temaExamen en localStorage para poder usarla en otra ventana usando otro script */
        localStorage.setItem('tema', temaExamen);
        localStorage.setItem('asignatura', asignaturaExamen);
        localStorage.setItem('nivel', nivelExamen);

        //Añadimos una nueva clase al elemento clicado para poder dar la opción al usuario que lo edite posteriormente
        tema.classList.add("elementoEditar");

        //Eliminamos la clase asignatura__contenido para evitar conflictos cuando se vaya a editar el tema
        tema.classList.remove("asignatura__contenido");

        //Ocultamos todos los elementos que no correspondan al tema clicado, nos valemos de la nueva clase que le hemos dado
        for(let i=0; i<temaAsignatura.length; i++) {

            let comparacion = temaAsignatura[i].className.indexOf("elementoEditar");

            if(comparacion === -1) {
                ocultar(temaAsignatura[i]);
            }
        }
        /*Eliminamos todos los contenedores de asignaturas que no correspondan a la selección del usuario, para eso usamos el id del contenedor (idPadre) */
        asignaturaLista.forEach((asignatura) => {
            if(asignatura.querySelector(".contenido").getAttribute("id") !== idPadre) {
                ocultar(asignatura);
            }
        })
        /*Eliminamos los contenedores de los niveles de cada grado, para que solo quede el nivel, asignatura y tema que ha seleccionado el usuario */
        nivelesTemas.forEach((tema) => {
            if(tema.getAttribute("id") !== document.querySelector(`#${idAbuelo}`).parentElement.getAttribute("id") ) {
                ocultar(tema);
            }
        })
        
        mostrar(numeroPreguntas);
        mostrar(numeroPreguntas.querySelector(".btnEnviar"));
        limpiar(inputs[0]);
        siguienteSeccion(elementoSiguiente);
    })
})

/*FUNCIÓN ELEGIR CANTIDAD DE PREGUNTAS */
//Función para almacenar el número de preguntas con las que se generará el test. Lo que hará será "escuchar" cuando se le dé a la tecla enter para ejecutar la función, y la segunda para cuando se de click en el btn enviar
const handleInput = (input, btnEnviar, btnEditar, field) => {
    let inputValue = Number(input.value);

    if(field === 'numPreguntas') {

        if((inputValue > 0) && (inputValue <= 20)) {

            numeroTotalPreguntas = inputValue;
            localStorage.setItem('numPreguntas', numeroTotalPreguntas);
            enviarElemento(input, btnEditar, btnEnviar);

            if(!numOpcionesMostrado) {
                mostrar(numeroRespuestas);
                mostrar(btnsEnviar[1]);
                numOpcionesMostrado = true; //Marcar que se mostró numOpciones
            }

            limpiar(inputs[1]);
            siguienteSeccion(numeroPreguntas);

        } else {
            limpiar(input);
        }
    } else if(field === "numOpciones") {

        if((inputValue > 1) && (inputValue <= 4)) {
            numeroTotalRespuestas = inputValue;
            localStorage.setItem('numRespuestas', numeroTotalRespuestas);
            enviarElemento(input, btnEditar, btnEnviar);
            mostrar(btnGenerar);

            siguienteSeccion(numeroRespuestas);
        } else {
            limpiar(input);
        }
    }
}
inputs.forEach((input) => {
    input.addEventListener("keydown", (e) => {
        let field = input.getAttribute("id");
        let btnEnviar = input.nextElementSibling;
        let btnEditar = btnEnviar.nextElementSibling;

        if(e.key == 'Enter') {
            handleInput(input, btnEnviar,btnEditar, field);
        }
    })   
});
//Función para almacenar el número de opciones de respuesta con las que se generará el test, la primera al darle enter y la segunda al hacer click
btnsEnviar.forEach((btn) => {
    btn.addEventListener("click", () => {
        let btnEditar = btn.nextElementSibling;
        let input = btn.previousElementSibling;
        let field = input.getAttribute("id");
        
        handleInput(input, btn, btnEditar, field);
    })
})
/*FUNCION PARA EDITAR*/
btnsEditar.forEach((btn) => {
    btn.addEventListener("click", () => {
        let btnEnviar = btn.previousElementSibling;
        let input = btnEnviar.previousElementSibling;
        editarElemento(input, btnEnviar, btn);
    });
})

/*Al hacer click en el btnGenerar me abre una nueva ventana donde aparece el examen generado */
btnGenerar.addEventListener("click", () => {
    window.open("test.html", "_blank");
})
/*Función para facilitar la experiencia de usuarion, una vez que se ha hecho enter/click automáticamente pasa al siguiente campo a completar */
const siguienteSeccion = (elementoClicado) => {
    let currentSection = document.querySelector('section:target') || document.querySelector(`#${elementoClicado.getAttribute("id")}`);
    let nextSection = currentSection.nextElementSibling;

    if(nextSection) {
        nextSection.scrollIntoView({behavior : 'smooth'});
        history.replaceState(null, null, `#${nextSection.id}`);
    }
}