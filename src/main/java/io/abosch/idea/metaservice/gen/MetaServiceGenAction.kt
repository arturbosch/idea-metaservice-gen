package io.abosch.idea.metaservice.gen

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtFile

class MetaServiceAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val psi = event.getData(CommonDataKeys.PSI_FILE) ?: return

        if (virtualFile.extension in SUPPORTED_EXTENSIONS) {
            generateService(psi)
        }
    }

    private fun generateService(psi: PsiFile) {
        val (serviceName, implName) = when (psi) {
            is KtFile -> psi.findFirstSuperName() ?: return
            else -> return
        }
        psi.createServiceFile(serviceName, implName)
    }

    companion object {
        internal val SUPPORTED_EXTENSIONS = setOf("kt", "java")
    }
}
