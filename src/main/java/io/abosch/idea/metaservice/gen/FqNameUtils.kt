package io.abosch.idea.metaservice.gen

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.unsafeResolveToDescriptor
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode

internal fun KtFile.findFirstSuperName(): NamePair? {
    val klass = declarations
        .filterIsInstance<KtClass>()
        .firstOrNull()

    if (klass?.superTypeListEntries?.size == 1) {
        val superDescriptors = klass.getSuperDescriptors()

        if (superDescriptors.isNotEmpty()) {
            return NamePair(
                superDescriptors.first().fqNameSafe.asString(),
                checkNotNull(klass.fqName?.asString())
            )
        }
    }

    return null
}

private fun KtClass.getSuperDescriptors(): Collection<ClassDescriptor> {
    val descriptor = unsafeResolveToDescriptor(BodyResolveMode.PARTIAL) as? ClassDescriptor
        ?: return emptyList()
    val superDescriptors = mutableListOf<ClassDescriptor>()
    for (supertype in descriptor.typeConstructor.supertypes) {
        val superDescriptor = supertype.constructor
            .declarationDescriptor as? ClassDescriptor
            ?: continue
        superDescriptors.add(superDescriptor)
    }
    return superDescriptors
}
