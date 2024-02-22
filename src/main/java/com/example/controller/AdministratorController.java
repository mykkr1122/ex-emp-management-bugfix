package com.example.controller;

// import java.util.HashMap;
// import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.LoginForm;
import com.example.service.AdministratorService;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HttpSession session;


	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：管理者を登録する
	/////////////////////////////////////////////////////
	/**
	 * 管理者登録画面を出力します.
	 * 
	 * @return 管理者登録画面
	 */
	@GetMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param form 管理者情報用フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@PostMapping("/insert")
	public String insert(@Validated InsertAdministratorForm insertAdministratorForm
	,BindingResult insertBindingResult
	,RedirectAttributes inserRedirectAttributes
	,Model model) {
		
		String mailAddress=insertAdministratorForm.getMailAddress();
		String password=insertAdministratorForm.getPassword();
		String confirmPassword=insertAdministratorForm.getConfirmPassword();
		
		//入力値にエラーがある場合、エラーメッセージを表示
		if (insertBindingResult.hasErrors()) {
			return "administrator/insert";
		}
		//メールアドレスが既に登録されている場合、エラーメッセージを表示
		if (administratorService.findByMailAddress(mailAddress) !=null){
			inserRedirectAttributes.addFlashAttribute("errorMessage", "このメールアドレスは既に登録されています");
			return "redirect:/toInsert";
		}
		//パスワードと確認用パスワードが一致しない場合、エラーメッセージを表示
		if (!password.equals(confirmPassword)) {
			model.addAttribute("confirm_errorMessage", "パスワードが一致しません");
			return "administrator/insert";
		}
		
		Administrator administrator = new Administrator();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// パスワードをハッシュ化
		String hashedPassword = passwordEncoder.encode(password);
		
		// フォームからドメインにプロパティ値をコピー
		BeanUtils.copyProperties(insertAdministratorForm, administrator);
		administrator.setPassword(hashedPassword);
		// ハッシュ化したパスワードでAdministratorを保存
		administratorService.insert(administrator);
		return "redirect:/";
	}





	/////////////////////////////////////////////////////
	// ユースケース：ログインをする
	/////////////////////////////////////////////////////
	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@GetMapping("/")
	public String toLogin(LoginForm loginForm,Model model) {
		return "administrator/login";
	}

	/**
	 * ログインします.
	 * 
	 * @param form 管理者情報用フォーム
	 * @return ログイン後の従業員一覧画面
	 */
	@PostMapping("/login")
	public String login(@Validated LoginForm loginForm
	,BindingResult result
	,RedirectAttributes redirectAttributes
	,Model model) {
		String mail=loginForm.getMailAddress();
		String pass=loginForm.getPassword();

		Administrator administrator = administratorService.login(mail, pass);
		if (result.hasErrors() || administrator==null) {
			//エラーメッセージが重複するためコメントアウト
			// if (administrator==null) {
			// 	redirectAttributes.addFlashAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
			// }
			return "administrator/login";
		}
		session.setAttribute("administratorName", administrator.getName());
		return "redirect:/employee/showList";
	}

	/////////////////////////////////////////////////////
	// ユースケース：ログアウトをする
	/////////////////////////////////////////////////////
	/**
	 * ログアウトをします. (SpringSecurityに任せるためコメントアウトしました)
	 * 
	 * @return ログイン画面
	 */
	@GetMapping(value = "/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

	/*
	 * 例外を発生させるメソッド
	 */
	@GetMapping("/exception")
	public String throwsException() {
		System.out.println("例外発生前");
		System.out.println(10 / 0); 
		System.out.println("例外発生後");

		return "さんぷる";
	}

}
