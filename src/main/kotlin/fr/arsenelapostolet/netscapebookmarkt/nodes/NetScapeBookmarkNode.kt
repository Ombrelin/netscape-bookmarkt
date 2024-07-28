package fr.arsenelapostolet.netscapebookmarkt.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = Serializer.NetScapeBookmarkNodeSerializer::class)
interface NetScapeBookmarkNode {
    
    val title: String
    
    val addDate: String?
    
    val lastModified: String?
}