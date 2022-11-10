$(document).ready(function() {
// on ready
});

async function registrarUsuarios(){
       let datos = {};
       datos.nombre = document.getElementById('txtNombre').value;
       datos.apellido = document.getElementById('txtApellido').value;
       datos.email = document.getElementById('txtEmail').value;
       datos.password = document.getElementById('txtPassword').value;

       let repetirPassword = document.getElementById('txtRepetirPassword').value;
       

       if (repetirPassword != datos.password){
            alert("La contraseña no es igual");
            return;
       }else{
       datos.repeat = repetirPassword;
       }
      const request = await fetch('api/registrar', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)

      });
      const respuesta = await request.text();
  

      if(request.status != 400){
         window.location.href = 'login.html'
      }
      else{
        let message = document.getElementById('message').outerHTML = respuesta

      }
}