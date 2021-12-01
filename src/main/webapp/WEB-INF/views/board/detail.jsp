<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
<div class="container">
    <div class="form-group">
        <h3>${board.title}</h3>
    </div>
    <hr/>
    <div class="form-group">
        <div>
            ${board.content}
        </div>
    </div>
    <hr/>
</div>
<%@ include file="../layout/footer.jsp" %>
