import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

// Just a example tasks to learn
open class GreetTask : DefaultTask() {
    private lateinit var name: String

    @Option(option = "name", description = "Configures the name to greet")
    fun setGreetName(name: String) {
        this.name = name
    }

    @Input
    fun getGreetName(): String {
        return name
    }

    @TaskAction
    fun greet() {
        println("Hello: $name")
    }
}
