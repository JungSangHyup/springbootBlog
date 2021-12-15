<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<div class="container">
    <form>
        <input type="hidden" id="id" value="${board.id}"/>
        <div class="form-group">
            <input type="${board.title}" name="title" class="form-control" id="title" placeholder="Enter Title"
            value="${board.title}">
        </div>

        <div class="form-group">
            <textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
        </div>
        <button id="btn-update" class="btn btn-primary">글수정</button>
    </form>
</div>
<script>
    $('.summernote').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 300
    });
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>
