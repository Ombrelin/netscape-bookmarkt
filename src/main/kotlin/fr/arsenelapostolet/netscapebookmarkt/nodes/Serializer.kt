package fr.arsenelapostolet.netscapebookmarkt.nodes

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*

object Serializer {
    val jsonSerializer = Json {
        serializersModule = SerializersModule() {
            polymorphic(NetScapeBookmarkNode::class) {
                subclass(Bookmark::class)
                subclass(Folder::class)
            }
        }
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
    
    object NetScapeBookmarkNodeSerializer : JsonContentPolymorphicSerializer<NetScapeBookmarkNode>(NetScapeBookmarkNode::class) {
        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<NetScapeBookmarkNode> {
            return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
                "bookmark" -> Bookmark.serializer()
                "folder" -> Folder.serializer()
                else -> Folder.serializer()
            }
        }
    }
}