package fr.arsenelapostolet.netscapebookmarkt.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


interface NetScapeBookmarkNode {
    
    val title: String
    
    @SerialName("add_date")
    val addDate: String?
    
    @SerialName("last_modified")
    val lastModified: String?
}