/*Función para conectar con la BBDD y traer los datos para generar el examen */
document.addEventListener('DOMContentLoaded', async () => {

  await loadGrades();
  await loadSubjects();
  await loadContents();
  await loadContentsPerLevel();
  await loadPreguntas();
  await loadRespuestas();
});

//Variables del DOM
// Recuperar las variables de localStorage, las traigo del scriptGenerar
const asignatura = localStorage.getItem('asignatura'); //idSubject
const nivel = localStorage.getItem('nivel'); //idGrade
const tema = localStorage.getItem('tema'); //idContent
const numRespuestas = Number(localStorage.getItem('numRespuestas'));
const numPreguntas = Number(localStorage.getItem('numPreguntas'));

let formulario = document.querySelector("#examen");

// Muestro el título del examen generado y el contenido que se evalua
let tituloExamen = document.createElement('h1');
    tituloExamen.setAttribute('class','examen__title');
    tituloExamen.textContent = `Examen de ${asignatura}`;
    formulario.appendChild(tituloExamen);

let temaExamen = document.createElement(`p`);
    temaExamen.setAttribute('class','examen__paragraph');
    temaExamen.textContent = `${tema}`;
    formulario.appendChild(temaExamen);

//Creo los contenedores de las preguntas y las respuestas
for(let n=0; n<numPreguntas; n++) {

  let contenedor = document.createElement('div');

      contenedor.setAttribute('class','opciones');
      contenedor.setAttribute('id',`contenedor${[n]}`);

  let enunciadoPregunta = document.createElement('h2');
      enunciadoPregunta.setAttribute('class', `pregunta__texto`);
      contenedor.appendChild(enunciadoPregunta);
      formulario.appendChild(contenedor);
}

let contenedorRespuesta = document.querySelectorAll(".opciones");

/*Variables globales. */
let enumPregunta = document.querySelectorAll(".pregunta__texto");
//variable para guaradar el id de la asignatura para el examen
let idSubject;
//variable para guaradar el id del contenido para el examen
let idContent;
//variable para guaradar el id del grado/nivel para el examen
let idGrade;
//variable para guaradar el id del contenido por nivel para poder extraer las preguntas
let idCPL;
//variable para almacenar los id de las preguntas y extraer las respuestas
let idPreguntas= [];
//variables para almacenar las preguntas/respuestas que se correspondan con el id de contenidos por tema
let listadoPreguntas = [];

//Variables para almacenar las preguntas que se muestran en el examen generado
let arrayControlId = [];
let arrayControlPregunta = [];
let respuestaCorrectaPorPregunta = {};


const loadGrades = async () => {
  try {
    //Accedemos a la tabla grades para obtener el id en base a la selección del usuario
    const rawResponse = await fetch("http://127.0.0.1:8080/api/grades");
    if(!rawResponse.ok) {
      throw new Error(`HTTP error! status: ${rawResponse.status}`);
    }
    const gradesExamen = await rawResponse.json();
    
    //Con el valor que hemos obtenido del formulario completado por el usuario obtendremos el que nos valdrá para las comparaciones en la BBDD

    idGrade = gradesExamen.find(grade => grade.name === nivel)?.id;

  } catch (error) {
    console.log('Error al obtener datos de la tabla ',error);
  }
}
const loadSubjects = async () => {
  try {
    //Accedemos a la tabla grades para obtener el id en base a la selección del usuario
    const rawResponse = await fetch("http://127.0.0.1:8080/api/subjects");
    if(!rawResponse.ok) {
      throw new Error(`HTTP error! status: ${rawResponse.status}`);
    }
    const subjectsExamen = await rawResponse.json();
    
    //Con el valor que hemos obtenido del formulario completado por el usuario obtendremos el id que nos valdrá para las comparaciones en la BBDD

    idSubject = subjectsExamen.find(subject => subject.name === asignatura)?.id;

  } catch (error) {
    console.log('Error al obtener datos de la tabla ',error);
  }
}

const loadContents = async () => {
  try {
    //Accedemos a la tabla grades para obtener el id en base a la selección del usuario
    const rawResponse = await fetch("http://127.0.0.1:8080/api/contents");
    if(!rawResponse.ok) {
      throw new Error(`HTTP error! status: ${rawResponse.status}`);
    }
    const contentsExamen = await rawResponse.json();
    
    //Con el valor que hemos obtenido del formulario completado por el usuario obtendremos el id que nos valdrá para las comparaciones en la BBDD

    idContent = contentsExamen.find(content => content.name === tema)?.id;
  
  } catch (error) {
    console.log('Error al obtener datos de la tabla ',error);
  }
}

const loadContentsPerLevel = async () => {
  try {
    //Accedemos a la tabla contents_per_level para poder obtener el id que necesitamos para extraer las preguntas que se correspondan con la selección del usuario
    const rawResponse = await fetch("http://127.0.0.1:8080/api/contents_per_level");
    
    if(!rawResponse.ok) {
      throw new Error(`HTTP error! status: ${rawResponse.status}`);
    }

    const contentsPerLevelExamen = await rawResponse.json();

    idCPL = contentsPerLevelExamen.find(contentLevel => contentLevel.contentsId && contentLevel.gradesId === idGrade)?.id;

  } catch (error) {
    console.log('Error al obtener datos de la tabla ',error);
  }
}

const loadPreguntas = async () => {
  
  try {
    const rawResponse = await fetch("http://127.0.0.1:8080/api/questions");
    if(!rawResponse.ok) {
      throw new Error(`HTTP error! status: ${rawResponse.status}`);
    }
    const preguntasExamen = await rawResponse.json();

    /*Lo primero que tiene que hacer en esta parte es sacar las preguntas del .json que se correspondan con el contenido y el nivel */
    for(let i=0; i<preguntasExamen.length; i++) {

      //Comparo el id de las preguntas con el idContentsPerlevel
      if((preguntasExamen[i].contents_per_grades) === idCPL) {

        //Cuando obtengo una coincidencia guardo la pregunta y el id en sus respectivos arrays
        listadoPreguntas.push(preguntasExamen[i].question);
        idPreguntas.push(preguntasExamen[i].id);
      }

    }
    
    //Con el nuevo array imprimimos las preguntas en nuestro front
    for(let j=0; j< numPreguntas; j++) {
      //Usamos la variable de posición para obtener un numero aleatorio que se corresponda con la longitud del preguntasExamen
      let poxAleatoria = Math.floor(Math.random() * listadoPreguntas.length);
        
      let yaExiste = arrayControlId.includes(idPreguntas[poxAleatoria]);

      if (!yaExiste && listadoPreguntas[poxAleatoria]) {
      
        enumPregunta[j].textContent = `${j+1}- ${listadoPreguntas[poxAleatoria]}`;

        //Guardamos el valor extraido del json en el arrayControl
        let introducirPregunta = listadoPreguntas[poxAleatoria];
        let introducirId = idPreguntas[poxAleatoria];

        arrayControlPregunta.push(introducirPregunta);
        arrayControlId.push(introducirId);
      } else {
        j--
      }
    }   /*FIN CODIGO LÓGICO */
  } catch(error) {
      console.log('Error al obtener datos de preguntas ',error);
    }
}


const loadRespuestas = async () => {

  try {
    const rawResponse = await fetch("http://127.0.0.1:8080/api/answers");
    if(!rawResponse.ok) {
      throw new Error(`HTTP error! status: ${rawResponse.status}`);
    }
    const respuestasExamen = await rawResponse.json();
    
    /*INICIO CODIGO LÓGICO */
    for(let i=0; i<arrayControlId.length; i++){

      for(let j=0; j<respuestasExamen.length; j++) {
          
          if(arrayControlId[i] === respuestasExamen[j].questions_id) {
            //Atributos de los label
            const attributesLabel = 'opcion flex align-items gap-2 label';

            let label = document.createElement('label');
                label.setAttribute('class', attributesLabel);
              
            let input = document.createElement('input');
                input.setAttribute('type','radio');
                input.setAttribute('class','input');
                input.setAttribute('required', 'required');
                input.setAttribute('name', `respuesta${i}`);

            let spanElement = document.createElement('span');
                spanElement.textContent = respuestasExamen[j].text;

            //Este contenedor lo usaremos para personalizar la apariencia de los input:radio
            let estilosRadio = document.createElement('div');
                estilosRadio.setAttribute('class', 'selector');

            //Le indicamos donde va cada elemento HTML
            label.appendChild(input);
            label.appendChild(estilosRadio);
            label.appendChild(spanElement);
            contenedorRespuesta[i].appendChild(label);

            if(respuestasExamen[j].is_right === true) {

              //Por cada pregunta, extraigo la respuesta correcta y la guardo en un objeto, en pares, donde por un lado la key será el identificador de la pregunta y el value la respuesta correcta de la pregunta

              respuestaCorrectaPorPregunta[contenedorRespuesta[i].getAttribute('id')] = respuestasExamen[j].text;
            }
          }
        }
    }
  }   /*FIN CODIGO LÓGICO */
  catch (error) {
    console.log('Error al obtener datos de answers: ',error);
  }
}

document.querySelector(".btnCorregir").addEventListener('click', () => {

  for(let [pregunta, respuestaCorrecta] of Object.entries(respuestaCorrectaPorPregunta)) {

    let respuestaSeleccionada = formulario.querySelector(`input[name="${pregunta}"]:checked`);

    if(respuestaSeleccionada && respuestaSeleccionada.value === respuestaCorrecta) {
      console.log(pregunta, respuestaCorrecta);
    }
  }
})
