package fr.arsenelapostolet.netscapebookmarkt

import fr.arsenelapostolet.netscapebookmarkt.nodes.NetScapeBookmarkNode
import org.jsoup.Jsoup
import kotlin.io.path.Path

class BookmarksParser {
    
    fun parse(fileName: String): List<NetScapeBookmarkNode> {
        val document = Jsoup.parse(Path(fileName))
        val dls = document.getElementsByTag("dl");
        val dl = dls.first();
        
        throw NotImplementedError()
    }
    
}