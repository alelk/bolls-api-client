import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldBe

class BollsApiClientIntegrationTest : FeatureSpec({

  tags(Config.Tags.integrationTest, Config.Tags.realBollsApi)

  val client = BollsApiClient(Config.BOOLS_API_BASE_URL)

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

})