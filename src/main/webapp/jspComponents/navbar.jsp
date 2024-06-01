<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="bp-nav-container">
    <div class="container-fluid">
        <nav class="navbar navbar-expand-lg my-nav">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <div class="my-menu">
                        <div></div>
                        <div></div>
                        <div></div>
                    </div>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/Home">
                    <img src="./assets/svg/logo.svg" alt="logo">
                    <span class="first">B</span><span class="txt">ook</span>
                    <span class="second">P</span><span class="txt">ad</span>
                </a>
                <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                    <ul class="navbar-nav me-auto mb-lg-0">
                        <form class="my-search  me-4" method="GET" action="${pageContext.request.contextPath}/Search">
                            <div class="content">
                                <input type="hidden" name="type" value="title">
                                <input class="" type="text" placeholder="Search ..." aria-label="Search" name="title_name">
                                <button class="" type="submit">
                                    <i class="bi bi-search"></i>
                                </button>
                            </div>
                        </form>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">Generi</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDarkDropdownMenuLink">
                                <c:set var="genres_list" scope="application" value='${genres_list}'/>
                                <c:forEach items="${genres_list}" var="genre">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Genres?genre_name=${genre.name}">${genre.name}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </ul>
                    <ul class="navbar-nav mb-2 mb-lg-0 me-0">
                        <li class="nav-item d-flex align-items-center">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Login">Accedi</a>
                        </li>
                        <li class="nav-item d-flex align-items-center me-4">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Reg">Registrati</a>
                        </li>
                        <li class="nav-item d-flex align-items-center">
                            <a class="nav-link switch-mode-icon">
                                <i class="bi switch-mode-style"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>