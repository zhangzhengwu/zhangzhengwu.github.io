// Download From http://www.sharejs.com
// Timer Bar - Version 1.2
// Author: Brian Gosselin of http://scriptasylum.com
// RELEASE INFO:
// V 1.0 - INTIAL RELEASE
// V 1.1 - CHANGED THE action() FUNCTION SO HIDING THE BAR IS SELECTABLE.
// V 1.2 - ADDED FUNCTIONALITY TO CONTROL WHEN YOU CLICK THE TIMERBAR.

var loadedcolor='darkgray' ;       // PROGRESS BAR COLOR
var unloadedcolor='lightgrey';     // COLOR OF UNLOADED AREA
var bordercolor='navy';            // COLOR OF THE BORDER
var barheight=15;                  // HEIGHT OF PROGRESS BAR IN PIXELS
var barwidth=300;                  // WIDTH OF THE BAR IN PIXELS
var waitTime=6;                   // NUMBER OF SECONDS FOR PROGRESSBAR

// THE FUNCTION BELOW CONTAINS THE ACTION(S) TAKEN ONCE BAR REACHES 100%.
// IF NO ACTION IS DESIRED, TAKE EVERYTHING OUT FROM BETWEEN THE CURLY BRACES ({})
// PRESENTLY, IT IS SET TO DO NOTHING, BUT CAN BE CHANGED EASILY.
// TO CAUSE A REDIRECT TO ANOTHER PAGE, INSERT THE FOLLOWING LINE:
// document.location.href="http://redirect_page.html";
// JUST CHANGE THE ACTUAL URL OF COURSE :)

var action=function()
{
hidebar();
//alert(waitTime+' seconds have elapsed.');
}

// THE FUNCTION BELOW CONTAINS THE ACTION(S) TO TAKE PLACE IF THE USER
// CLICKS THE TIMERBAR. THIS CAN BE USED TO CANCEL THE TIMERBAR.
// IF YOU WISH NOTHING TO HAPPEN, SIMPLY REMOVE EVERYTHING BETWEEN THE CURLY BRACES.

var clickBar=function(){
hidebar();
alert('Timer cancelled.');
}

//*****************************************************//
//**********  DO NOT EDIT BEYOND THIS POINT  **********//
//*****************************************************//

var ns4=(document.layers)?true:false;
var ie4=(document.all)?true:false;
var blocksize=(barwidth-2)/waitTime/10;
var loaded=0;
var PBouter;
var PBouter_all;
var PBdone;
var PBbckgnd;
var Pid=0;
var txt='';
if(ns4){
	
txt+='<table border=0 cellpadding=0 cellspacing=0><tr><td>';
txt+='<ilayer name="PBouter" visibility="hide" height="'+barheight+'" width="'+barwidth+'" >';
txt+='<layer width="'+barwidth+'" height="'+barheight+'" bgcolor="'+bordercolor+'" top="0" left="0"></layer>';
txt+='<layer width="'+(barwidth-2)+'" height="'+(barheight-2)+'" bgcolor="'+unloadedcolor+'" top="1" left="1"></layer>';
txt+='<layer name="PBdone" width="'+(barwidth-2)+'" height="'+(barheight-2)+'" bgcolor="'+loadedcolor+'" top="1" left="1"></layer>';
txt+='</ilayer>';
txt+='</td></tr></table>';

}else{
txt+="<div id='PBouter_all' style='background-color:gray; width:100%;display:none; height:100%;z-index:1000;position:absolute; filter:alpha(opacity=70); opacity:0.70;'>"
	txt+="<span style='position:absolute; color:blue; width:100%;left:620px;top:280px;'>Being processed...</span>";

txt+='<div id="PBouter"  style="position:relative; display:none; left:500px;top:300px; background-color:'+bordercolor+'; width:'+barwidth+'px; height:'+barheight+'px;">';
txt+='<div style="position:absolute; top:1px; left:1px; width:'+(barwidth-2)+'px; height:'+(barheight-2)+'px; background-color:'+unloadedcolor+'; font-size:1px;"></div>';
txt+='<div id="PBdone" style="position:absolute; top:1px; left:1px; width:0px; height:'+(barheight-2)+'px; background-color:'+loadedcolor+'; font-size:1px;"></div>';
txt+='</div>';
 
 txt+='</div>';
}

document.write(txt);

function incrCount(){
loaded++;
if(loaded<0)loaded=0;
if(loaded>=waitTime*10){
clearInterval(Pid);
loaded=waitTime*10;
setTimeout('action()',100);
}
resizeEl(PBdone, 0, blocksize*loaded, barheight-2, 0);
}

function hidebar(){
clearInterval(Pid);
if(ns4){
	PBouter.display="none";
	PBouter_all.display="none";
}
else{
	PBouter.style.display="none";
	PBouter_all.style.display="none";
}
}

//THIS FUNCTION BY MIKE HALL OF BRAINJAR.COM
function findlayer(name,doc){
var i,layer;
for(i=0;i<doc.layers.length;i++){
layer=doc.layers[i];
if(layer.name==name)return layer;
if(layer.document.layers.length>0)
if((layer=findlayer(name,layer.document))!=null)
return layer;
}
return null;
}

function progressBarInit(num){
waitTime=num;
blocksize=(barwidth-2)/waitTime/10;
 
PBouter=(ns4)?findlayer('PBouter',document):(ie4)?document.all['PBouter']:document.getElementById('PBouter');
PBdone=(ns4)?PBouter.document.layers['PBdone']:(ie4)?document.all['PBdone']:document.getElementById('PBdone');
PBouter_all=(ns4)?findlayer('PBouter_all',document):(ie4)?document.all['PBouter_all']:document.getElementById('PBouter_all');
resizeEl(PBdone,0,0,barheight-2,0);
if(ns4){
	PBouter.display="";
	PBouter_all.display="";
}
else{ PBouter.style.display="";
	PBouter_all.style.display="";
}
Pid=setInterval('incrCount()',95);
  loaded=0;
}

function resizeEl(id,t,r,b,l){
if(ns4){
id.clip.left=l;
id.clip.top=t;
id.clip.right=r;
id.clip.bottom=b;
}else id.style.width=r+'px';
}

