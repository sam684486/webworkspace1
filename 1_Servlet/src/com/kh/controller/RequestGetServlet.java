package com.kh.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestGetServlet
 */
@WebServlet("/test.do")
public class RequestGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RequestGetServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("잘 .. 실행되남??");
		/*
		 * Get 방식으로 요청했다면 해당 doGet메소드가 호출됨.
		 * 
		 * 첫번째 매개변수인 HttpServletRequest request에는 요청 시 전달된 내용들이 담김(사용자가 입력한값,
		 * 요청 전송방식, 요청한 사용자의 ip등등)
		 * 
		 * 두번째 매개변수인 HttpServletResponse response에는 요청 처리 후 응답을 할 때 사용하는 객체
		 * 
		 * 우선, 요청을 처리하가 위해 요청시 전달된 값(사용자가 입력한 값)들을 뽑는다.
		 * request의 parameter 영역안에 존재=> key-value세트로 담겨잇다(name속성값-value속성값)
		 * 
		 * 따라서 request의 parameter영역으로부터 전달된 데이터를 뽑는 메소드로
		 * - request.getParameter("키값") : String(그에 해당하는 value값) = > 무조건 문자열형태로
		 * 반환되기 때문에 다른 자료형으로 변경하려면 파싱해줘야함
		 * - request.getparameterValues("키값") : String[](그에 해당하는 value값들) => 하나의 
		 * key값으로 여러개의 value값들을 전달받을경우(체크박스) 문자열 배열형으로 반환 가능
		 * 
		 */
		String name = request.getParameter("name"); // "민경민", ""(텍스트 상자가 빈경우 빈 문자열이 넘어감)
		String gender = request.getParameter("gender"); // "M"/"F" /null => 라디오 버튼 체크안한경우
		int age = Integer.parseInt(request.getParameter("age"));// "20", ""(값이 없는 경우, NumberFormatException 발생)
		
		// 체크박스와 같이 복수개의 정보를 받을때는 배열로 받아야한다.
		String[] foods = request.getParameterValues("food");// ["한식","일식"]/ null(체크한게 없다면 null반환)
		
		/*
		 * 이 뽑아낸 값들을 가지고 요청 처리해줘야함(db와 상호작용을 함)
		 * 보통의 흐름 : Service의 메소드 호출 시 뽑은 값들을 전달(Wrapping) - DAO 호출 - DB SQL문 결과값 반환
		 * 
		 * int result = new MemberService().inserMember(m);
		 * if(result >0) : 성공이라고 보고 commit, 실패시 rollback(Service가 처리)
		 * Service와 Dao단은 거의 변동없이 View와 Controller단의 소스코드상의 변화가 많을 것이다.
		 * 
		 * 위 요청처리를 다 했다는 가정하에 사용자가 보게될 응답페이지를 꾸밀예정.
		 * 
		 * 장점 : Java코드내에 작성하기 때문에 반복문, 조건문 등 유용한 메소드 활용이 가능
		 * 
		 * 단점 : 복잡, 혹시라도 추후 html을 수정하고자 한다면 java소스코드 내에서 수정이 이루어지기때문에
		 * 		 수정된 내용을 반영시키고자한다면 서버를 restart해줘야함.
		 * 
		 */
		// *response객체를 통해 사용자에게 html (응답화면)전달
		//1) 이제부터 내가 출력할(응답할) 내용은 문서형태의 HTML이고/ 문자셋은 UTF-8이라는 것을 지정
		response.setContentType("text/html; charset=UTF-8");
		
		//2) 응답하고자 하는 사용자와의 스트림을 생성
		PrintWriter out = response.getWriter();
		
		// 3) 생성된 스트림을 통해 응당 html구문을 한줄씩 출력
		out.println("<html>");
		out.println("<head>");
		out.println("<style>");
		out.println("<>");
		out.println("h2 {color:red}");
		out.println("#name {color:orange}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<h2>개인정보 응답화면</h2>");
		
		//out.println("<span id='name'>"+name+"</span>");
		out.println("<span id='name'>"+name+"</span>");
		out.println("<span id='age'>"+age+"</span>");
//		out.println("<span id='gender'>"+gender+"</span>");
		if(gender.equals("M")) {
			out.println("남자");
		}else if(gender.equals("F")) {
			out.println("여자");
		}
		out.println("<span id='city'>"+request.getParameter("city")+"</span>");
		out.println("좋아하는 음식은 ::");
		if(foods == null) {
			out.print("없습니다.");
		}else{
			
			out.println("<ul>");
			
			for(String food : foods) {
				out.printf("<li> %s </li>", food);
			}
			
			out.println("</ul>");
			
		}
		
		out.println("</body>");
		
		
		out.println("</html>");
		
		System.out.println(name);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
