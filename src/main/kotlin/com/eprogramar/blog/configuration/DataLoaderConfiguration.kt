package com.eprogramar.blog.configuration

import com.eprogramar.blog.model.Article
import com.eprogramar.blog.model.Author
import com.eprogramar.blog.model.Category
import com.eprogramar.blog.model.User
import com.eprogramar.blog.repository.ArticleRepository
import com.eprogramar.blog.repository.AuthorRepository
import com.eprogramar.blog.repository.CategoryRepository
import com.eprogramar.blog.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class DataLoaderConfiguration(
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository,
        private val articleRepository: ArticleRepository,
        private val authorRepository: AuthorRepository
) : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        loadUser()
        loadCategories()
        loadArticles()
    }

    private fun loadCategories() {
        if (categoryRepository.count() == 0L) {
            listOf(
                    Category(name = "Technology"),
                    Category(name = "World"),
                    Category(name = "U.S."),
                    Category(name = "Design"),
                    Category(name = "Culture"),
                    Category(name = "Business"),
                    Category(name = "Politics"),
                    Category(name = "Opinion"),
                    Category(name = "Science"),
                    Category(name = "Health"),
                    Category(name = "Style"),
                    Category(name = "Travel")
            ).also { categoryRepository.saveAll(it) }
        }
    }

    private fun loadUser() {
        if (userRepository.count() == 0L) {

            listOf(
                    User(
                            name = "Administrator",
                            email = "admin@blog.com",
                            password = "admin"
                    ),
                User(
                    name = "Vitoria",
                    email = "vitoria@blog.com",
                    password = "admin"
                )
            ).also { userRepository.saveAll(it) }
        }
    }

    private fun loadArticles() {
        if (articleRepository.count() == 0L) {
            val categoryTechnology = categoryRepository.findAll().get(0)
            val categoryWorld = categoryRepository.findAll().get(1)
            val authors = authorRepository.saveAll(
                    listOf(
                            Author(
                                    user = userRepository.findAll().get(0),
                                    about = "When an unknown printer took a galley of type and scrambled it to make a type specimen book."
                            ),
                            Author(
                                    user = userRepository.findAll().get(1),
                                    about = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
                            )
                    )
            )
            listOf(
                    Article(
                            title = "Formação Linguagem Kotlin",
                            subTitle = "Kotlin: Ampliando Horizontes no Desenvolvimento de Software",
                            content ="A linguagem Kotlin é uma linguagem de programação moderna desenvolvida pela JetBrains, com o objetivo de oferecer uma experiência de desenvolvimento mais agradável e eficiente. Ela foi projetada para ser concisa, expressiva e segura." ,
                                    date = LocalDateTime.now(),
                            author = authors.get(0),
                            category = categoryTechnology
                    ),
                    Article(
                            title = "Formação Kotlin e Spring Boot",
                            subTitle = "Desenvolvimento de uma API Rest",
                            content = "Spring Boot atualmente é o principal framework utilizado no mundo Java para o desenvolvimento de APIs Rest, sendo que ele também suporta outras linguagens, como o Kotlin.",
                            date = LocalDateTime.now(),
                            author = authors.get(1),
                            category = categoryWorld
                    )
            ).also { articleRepository.saveAll(it) }
        }
    }
}