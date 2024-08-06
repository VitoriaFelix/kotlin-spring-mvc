package com.eprogramar.blog.controller

import com.eprogramar.blog.model.Article
import com.eprogramar.blog.model.User
import com.eprogramar.blog.service.ArticleService
import com.eprogramar.blog.service.CategoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/article")
class ArticleController(
        private val categoryService: CategoryService,
        private val articleService: ArticleService
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun form(model: Model): String {
        logger.info("form()...")
        model.addAttribute("article", Article())
        model.addAttribute("categories", categoryService.findAll())
        return "article"
    }

    @PostMapping
    fun save(article: Article, session: HttpSession, redirectAttributes: RedirectAttributes): String {
        logger.info("save($article)")

        val currentUser = session.getAttribute("currentUser") as User
        articleService.save(article, currentUser)
        val messageSuccess = "Artigo criado com sucesso!!!"
        redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess)

        logger.info(messageSuccess)
        return "redirect:/"
    }

    @GetMapping("/list")
    fun list(model: Model):String {
        logger.info("list()...")
        model.addAttribute("articles", articleService.findAll())
        model.addAttribute("categories", categoryService.findAll())
        return "article-list"
    }

    @GetMapping("/list/user/{idUser}")
    fun listByAuthor(@PathVariable idUser: Long, model: Model):String {
        logger.info("list(idUser = $idUser)...")
        val sort = Sort.by(Sort.Direction.DESC, "id")
        model.addAttribute("articles", articleService.findByAuthorUserId(idUser, sort))
        model.addAttribute("categories", categoryService.findAll())
        return "article-list"
    }

    @GetMapping("/list/category/{idCategory}")
    fun listByCategory(@PathVariable idCategory: Long, model: Model):String {
        logger.info("list(idUser = $idCategory)...")
        val sort = Sort.by(Sort.Direction.DESC, "id")
        model.addAttribute("articles", articleService.findByCategoryId(idCategory, sort))
        model.addAttribute("categories", categoryService.findAll())
        return "article-list"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long,  model: Model): String {
        logger.info("edit(id = $id)...")
        model.addAttribute("article", articleService.findById(id))
        model.addAttribute("categories", categoryService.findAll())
        return "article"
    }

    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: Long,  model: Model): String {
        logger.info("delete(id = $id)...")

        articleService.deleteById(id)

        return "redirect:/article/list"
    }
}