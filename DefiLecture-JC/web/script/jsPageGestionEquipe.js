/* 
    This file is part of DefiLecture.

    DefiLecture is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DefiLecture is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DefiLecture.  If not, see <http://www.gnu.org/licenses/>.
*/

function supprimerEquipe(idEquipe){
  $.ajax({
        url: '*.do?tache=effectuerSuppressionEquipe&idEquipe='+idEquipe, 
        cache    : false,
        complete: function () {
          $("tr#equipe-"+ idEquipe).remove();
        }
    });
}

