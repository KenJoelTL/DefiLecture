/**
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

package com.defilecture.controleur;

import com.defilecture.modele.Compte;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mikael Nadeau
 */
abstract public class Action implements Executable, RequestAware, SessionAware {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }

    protected boolean userIsAdmin() {
        return (int)session.getAttribute("role") == Compte.ADMINISTRATEUR;
    }

    protected boolean userIsModerateur() {
        return (int)session.getAttribute("role") == Compte.MODERATEUR;
    }
}
