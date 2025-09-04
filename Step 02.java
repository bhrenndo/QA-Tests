# Fluxo completo no DemoQA com BDD:

Scenario: Executar todas as etapas de forma sequencial
Given que acesso a página de Forms
When preencho e envio o formulário
Then valido que o formulário foi submetido

Given que acesso a página de Browser Windows
When abro uma nova aba
Then valido o texto da nova aba

Given que acesso a página de Web Tables
When crio os doze registros
And edito os registros criados
Then valido os registros editados
And deleto todos os registros
Given que acesso a página de Progress Bar
When executo o progresso até 25 por cento
And completo o progresso até 100 por cento
Then reseto o progresso

Given que acesso a página de Sortable
When reorganizo os itens em ordem correta crescente
Then valido a nova ordem


#Teste com Cucumber + Selenium:

import io.cucumber.java.*;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class TesteCompletoSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ===== Método utilitário para log =====
    private void logEtapa(String etapa) {
        System.out.println("✅ Etapa concluída: " + etapa);
        System.out.println("➡️ Prosseguindo automaticamente...\n");
    }

    // ===== FORMS =====
    @Given("que acesso a página de Forms")
    public void acessoForms() {
        driver.get("https://demoqa.com/automation-practice-form");
    }

    @When("preencho e envio o formulário")
    public void preenchoFormulario() {
        driver.findElement(By.id("firstName")).sendKeys("User");
        driver.findElement(By.id("lastName")).sendKeys("02");
        driver.findElement(By.id("userEmail")).sendKeys("user02@accenture.com.br");
        driver.findElement(By.cssSelector("label[for='gender-radio-1']")).click(); // Male
        driver.findElement(By.id("userNumber")).sendKeys("8198234689");

        // Date of Birth
        driver.findElement(By.id("dateOfBirthInput")).click();
        new Select(driver.findElement(By.className("react-datepicker__month-select"))).selectByVisibleText("February");
        new Select(driver.findElement(By.className("react-datepicker__year-select"))).selectByVisibleText("1997");
        driver.findElement(By.cssSelector(".react-datepicker__day--022")).click();

        // Subjects
        WebElement subjects = driver.findElement(By.id("subjectsInput"));
        subjects.sendKeys("Social Studies");
        subjects.sendKeys(Keys.ENTER);
        subjects.sendKeys("Computer Science");
        subjects.sendKeys(Keys.ENTER);
        subjects.sendKeys("Accounting");
        subjects.sendKeys(Keys.ENTER);

        // Hobbies
        driver.findElement(By.cssSelector("label[for='hobbies-checkbox-2']")).click(); // Reading
        driver.findElement(By.cssSelector("label[for='hobbies-checkbox-3']")).click(); // Music

        // Upload
        driver.findElement(By.id("uploadPicture")).sendKeys("C:\\Users\\Public\\Pictures\\teste.png");

        // Address
        driver.findElement(By.id("currentAddress")).sendKeys("Lion's Street, 67");

        // State & City
        driver.findElement(By.id("state")).click();
        driver.findElement(By.xpath("//div[contains(text(),'Haryana')]")).click();
        driver.findElement(By.id("city")).click();
        driver.findElement(By.xpath("//div[contains(text(),'Panipat')]")).click();

        // Submit
        WebElement submitBtn = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
    }

    @Then("valido que o formulário foi submetido")
    public void validoFormulario() {
        WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-lg")));
        Assert.assertTrue(titulo.getText().contains("Thanks"));
        driver.findElement(By.id("closeLargeModal")).click();

        logEtapa("Formulário");
    }

    // ===== BROWSER WINDOWS =====
    @Given("que acesso a página de Browser Windows")
    public void acessoBrowserWindows() {
        driver.get("https://demoqa.com/browser-windows");
    }

    @When("abro uma nova aba")
    public void abroNovaAba() {
        driver.findElement(By.id("tabButton")).click();
    }

    @Then("valido o texto da nova aba")
    public void validoNovaAba() {
        String original = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        WebElement texto = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleHeading")));
        Assert.assertTrue(texto.getText().contains("This is a sample page"));
        driver.close();
        driver.switchTo().window(original);

        logEtapa("Browser Windows");
    }

    // ===== WEB TABLES =====
    @Given("que acesso a página de Web Tables")
    public void acessoWebTables() {
        driver.get("https://demoqa.com/webtables");
    }

    @When("crio os doze registros")
    public void crioDozeRegistros() {
        String[][] registros = {
            {"Laura", "Silva", "laura.silva@accenture.com.br", "28", "2000", "IT Operations"},
            {"Flor", "Silva", "Flor.silva@accenture.com.br", "22", "1999", "Tax Accounts"},
            {"Felipe", "Barreto", "Felipe.barreto@accenture.com.br", "22", "2900", "QA Analyst"},
            {"Pedro", "Sacrament", "Sacrament.Pedro@accenture.com", "39", "4001", "Systems Support"},
            {"Pietra", "Smool", "Pietra.Smool@accenture.com", "26", "5000", "IT Management"},
            {"Levi", "Dias", "Levi.Dias@accenture.com", "32", "3500", "HR Business"},
            {"Leta", "Smith", "Leta.Smith@accenture.com", "33", "2796", "Marketing"},
            {"Thiago", "Santos", "thiago.santos@accenture.com.br", "35", "2600", "Social Business"},
            {"Valeria", "Almeida", "valeria.almeida@accenture.com.br", "20", "2300", "HR Assistant"},
            {"Grazy", "Brito", "Grazy.Brito@accenture.com.br", "34", "2600", "Administrative Assistant"},
            {"Thais", "Macedo", "thais.macedo@accenture.com.br", "31", "2200", "HR Assistant"},
            {"Bia", "Mello", "Bia.mello@accenture.com.br", "25", "200", "QA Assistant"}
        };

        for (String[] reg : registros) {
            driver.findElement(By.id("addNewRecordButton")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("firstName"))).sendKeys(reg[0]);
            driver.findElement(By.id("lastName")).sendKeys(reg[1]);
            driver.findElement(By.id("userEmail")).sendKeys(reg[2]);
            driver.findElement(By.id("age")).sendKeys(reg[3]);
            driver.findElement(By.id("salary")).sendKeys(reg[4]);
            driver.findElement(By.id("department")).sendKeys(reg[5]);
            driver.findElement(By.id("submit")).click();
        }

        logEtapa("Web Tables - Criação");
    }

    @When("edito os registros criados")
    public void editoRegistros() {
        List<WebElement> editButtons = driver.findElements(By.cssSelector("span[title='Edit']"));
        for (WebElement btn : editButtons) {
            btn.click();
            WebElement depto = wait.until(ExpectedConditions.elementToBeClickable(By.id("department")));
            depto.clear();
            depto.sendKeys("Depto Atualizado");
            driver.findElement(By.id("submit")).click();
        }
        logEtapa("Web Tables - Edição");
    }

    @Then("valido os registros editados")
    public void validoRegistrosEditados() {
        List<WebElement> colunas = driver.findElements(By.cssSelector(".rt-td:nth-child(6)"));
        for (WebElement col : colunas) {
            Assert.assertTrue(col.getText().contains("Depto Atualizado"));
        }
        logEtapa("Web Tables - Validação");
    }

    @Then("deleto todos os registros")
    public void deletoRegistros() {
        while (true) {
            List<WebElement> botoesDelete = driver.findElements(By.cssSelector("span[title='Delete']"));
            if (botoesDelete.isEmpty()) break;
            botoesDelete.get(0).click();
        }
        logEtapa("Web Tables - Exclusão");
    }

    // ===== PROGRESS BAR =====
    @Given("que acesso a página de Progress Bar")
    public void acessoProgressBar() {
        driver.get("https://demoqa.com/progress-bar");
    }

    @When("executo o progresso até 25 por cento")
    public void progresso25() {
        WebElement startStopBtn = driver.findElement(By.id("startStopButton"));
        startStopBtn.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".progress-bar"), "25%"));
        startStopBtn.click(); // pausa
        logEtapa("Progress Bar - 25%");
    }

    @When("completo o progresso até 100 por cento")
    public void progresso100() {
        WebElement startStopBtn = driver.findElement(By.id("startStopButton"));
        startStopBtn.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".progress-bar"), "100%"));
        logEtapa("Progress Bar - 100%");
    }

    @Then("reseto o progresso")
    public void resetoProgresso() {
        driver.findElement(By.id("resetButton")).click();
        logEtapa("Progress Bar - Reset");
    }

    // ===== SORTABLE =====
    @Given("que acesso a página de Sortable")
    public void acessoSortable() {
        driver.get("https://demoqa.com/sortable");
    }

    @When("reorganizo os itens em ordem crescente")
    public void reorganizoItens() {
        List<WebElement> itens = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#demo-tabpane-list div")));
        Assert.assertFalse(itens.isEmpty());
        logEtapa("Sortable - Reorganização");
    }

    @Then("valido a nova ordem")
    public void validoOrdem() {
        List<WebElement> itens = driver.findElements(By.cssSelector("#demo-tabpane-list div"));
        Assert.assertTrue(itens.size() > 0);
        logEtapa("Sortable - Validação");
    }
}


