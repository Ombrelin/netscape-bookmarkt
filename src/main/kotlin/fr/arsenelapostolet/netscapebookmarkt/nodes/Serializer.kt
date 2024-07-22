package fr.arsenelapostolet.netscapebookmarkt.nodes

import kotlinx.serialization.modules.*

object Serializer {
    val module = SerializersModule() {
        polymorphic(NetScapeBookmarkNode::class) {
            subclass(Bookmark::class)
            subclass(Folder::class)
        }
    }
}