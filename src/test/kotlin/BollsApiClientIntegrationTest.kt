package io.github.alelk.bolls_api_client

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class BollsApiClientIntegrationTest : FeatureSpec({

  tags(Config.Tags.integrationTest, Config.Tags.realBollsApi)

  val client = BollsApiClient(BollsApiClientConfig(baseUrl = Config.BOOLS_API_BASE_URL))

  feature("get languages and translations") {
    scenario("get all translations") {
      val translationsByLanguage = client.getAllTranslations()
      translationsByLanguage.forEach {
        it.language.isEmpty() shouldBe false
        it.translations shouldHaveAtLeastSize 1
        it.translations.forEach { t ->
          t.shortName.isEmpty() shouldBe false
          t.fullName.isEmpty() shouldBe false
        }
      }
    }
  }

  feature("get books info by translation") {
    scenario("get KJV books info") {
      val books = client.getBooksInfoByTranslation("KJV")
      books shouldHaveSize 81
      val book2 = books.find { it.bookId == 2 }
      book2 shouldNotBe null
      book2!!.name shouldBe "Exodus"
      book2.chronOrder shouldBe 3
      book2.countChapters shouldBe 40
    }
  }

  feature("get chapter text") {
    scenario("get NKJV chapter 1 text with comments") {
      val verses = client.getChapterText("NKJV", 1, 1)
      verses shouldHaveSize 31
      verses[0].verse shouldBe 1
      verses[0].text shouldBe "In the beginning God created the heavens and the earth."
      verses[0].comment shouldNotBe 0
    }
    scenario("get NKJV chapter 1 text without comments") {
      val verses = client.getChapterText("NKJV", 1, 1, false)
      verses shouldHaveSize 31
      verses[0].verse shouldBe 1
      verses[0].text shouldBe "In the beginning God created the heavens and the earth."
      verses[0].comment shouldBe null
    }
  }

  feature("get parallel texts") {
    scenario("get parallel texts for KJV, NKJV, YLT") {
      val parallelTexts = client.getParallelTexts(listOf("KJV", "NKJV", "YLT"), 1, 1, listOf(1, 2, 3, 4))
      parallelTexts.groups shouldHaveSize 3
      parallelTexts.groups.forEach { group ->
        group shouldHaveSize 4
        group.forEach {
          it.book shouldBe 1
          it.chapter shouldBe 1
          it.verse shouldBeIn listOf(1, 2, 3, 4)
        }
      }
      parallelTexts.groups[0][0].translation shouldBe "KJV"
      parallelTexts.groups[1][0].translation shouldBe "NKJV"
      parallelTexts.groups[2][0].translation shouldBe "YLT"
      parallelTexts.groups[0][0].text shouldBe "In the beginning God created the heaven and the earth."
      parallelTexts.groups[1][0].text shouldBe "In the beginning God created the heavens and the earth."
      parallelTexts.groups[2][0].text shouldBe "In the beginning of God's preparing the heavens and the earth --"
    }
  }

})