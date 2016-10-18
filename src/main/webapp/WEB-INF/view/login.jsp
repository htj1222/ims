<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>ims로그인</title>
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.userLoginInfo}">
			<h2>로그인 성공</h2>
        이름 : ${sessionScope.userLoginInfo.username}
 
        이메일 : <c:out value="${sessionScope.userLoginInfo.email}" />
			<a href="logout">로그아웃</a>
		</c:when>
		<c:otherwise>
			<h2>로그인</h2>
			<form name="form1" method="post" action="loginProcess">
				<table>
					<tr height="40px">
						<td>유저ID</td>
						<td><input type="text" name="userId"></td>
					</tr>
					<tr height="40px">
						<td>패스워드</td>
						<td><input type="password" name="password"></td>
					</tr>
				</table>
				<table>
					<tr>
						<td align="center"><input type="submit" value="로그인"></td>
						<td align="center"><input type="reset" value="리셋"></td>
					</tr>
				</table>
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>