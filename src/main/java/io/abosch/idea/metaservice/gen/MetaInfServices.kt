package io.abosch.idea.metaservice.gen

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiFile
import com.intellij.util.io.createDirectories
import org.jetbrains.kotlin.idea.util.projectStructure.module
import org.jetbrains.kotlin.idea.util.sourceRoots
import java.nio.file.Files
import java.nio.file.Paths

internal fun PsiFile.createServiceFile(serviceName: String, implName: String) {
    val resourceFile = findResourcesRoot()

    if (resourceFile == null) {
        val notification = Notification(
            "idea-metaservice-gen",
            "Could not find resources root.",
            """Intellij has not yet registered a resources root for module ${module?.name}.
                |We have created a resources directory and the services file.
                |Please reload your project to have the resources source root registered automatically.
            """.trimMargin(),
            NotificationType.WARNING
        )
        notification.notify(project)
        return
    }

    val serviceFile = Paths.get(resourceFile.path, "META-INF/services/$serviceName")
    serviceFile.parent.createDirectories()
    Files.write(serviceFile, "$implName\n".toByteArray(Charsets.UTF_8))
    VfsUtil.markDirtyAndRefresh(true, true, true, module?.moduleFile)
}

internal fun PsiFile.findResourcesRoot() = module?.sourceRoots?.find { it.name == "resources" }
