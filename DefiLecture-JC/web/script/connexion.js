/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    $("#identifiant").focus();
    $("#msgMDPoublie").hide();
    $("#lienMDPoublie").click(function(){
	$("#spanMDP").toggleClass("glyphicon-menu-up")
	$("#msgMDPoublie").slideToggle();
    });
});


