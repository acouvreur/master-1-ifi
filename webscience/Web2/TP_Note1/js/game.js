window.onload = init;

let canvas, ctx;
let ennemis = [];

// main.js
function init() {
  console.log("page chargee");
  
  // 1 On recupere un pointeur sur le canvas
  canvas = document.querySelector("#myCanvas");
  
  // 2 On recupere le contexte graphique pour dessiner
  // dans le canvas
  ctx = canvas.getContext("2d");
  
  // 3 on dessine pour verifier que ca marche
  //ctx.fillStyle = 'red';
  //ctx.fillRect(10, 10, 100, 100);
  rect1 = new Rectangle(10, 10, 30, 30, 'red');
  rect1.vitesseX = 5;
  rect1.vitesseY=3;
  ennemis.push(rect1);
  
   rect2 = new GrowingRectangle(110, 110, 100, 100, 'green');
    rect2.vitesseY = 2;
  ennemis.push(rect2);
  joueur = new Rectangle(20, 250, 10, 10, 'blue');
  
  // Ecouteurs de clavier
  window.onkeydown = traiteKeydown;
  window.onkeyup = traiteKeyup;

  // on demarre l'animation
  requestAnimationFrame(animation);
}



// Boucle d'animation
// typiquement dans game.js
function animation() {
  // 1 on efface
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  
  // 2 on dessine et on deplace
  dessineEtDeplaceLesObjets();
  
  // 3 on teste les collisions
  testeCollisions();
  
  // 4 on rappelle la boucle d'animation 60 fois / s
  requestAnimationFrame(animation);
}


function dessineEtDeplaceLesObjets() {
   ennemis.forEach((el) => {
     el.draw(ctx);
     el.move();
   })
   
   joueur.move();
   joueur.draw(ctx);
}

