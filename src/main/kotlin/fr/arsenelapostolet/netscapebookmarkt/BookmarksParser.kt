package fr.arsenelapostolet.netscapebookmarkt

import fr.arsenelapostolet.netscapebookmarkt.nodes.Bookmark
import fr.arsenelapostolet.netscapebookmarkt.nodes.Folder
import fr.arsenelapostolet.netscapebookmarkt.nodes.NetScapeBookmarkNode
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import kotlin.io.path.Path

class BookmarksParser {

    fun parse(fileName: String): Folder {
        val document = Jsoup.parse(Path(fileName))
        val rootDl = document
            .root()
            .children()
            .first { it.tagName().lowercase() == "html" }
            .children()
            .first { it.tagName().lowercase() == "body" }
            .children()
            .first { it.tagName().lowercase() == "dl" }

        return processFolder(rootDl)
    }

    private fun processFolder(dl: Element): Folder {
        val h3 = dl
            .children()
            .first {  it.tagName().lowercase() == "h3" }

        return Folder(
            h3.text(),
            extractNsRoot(h3),
            if (h3.attribute("ADD_DATE") != null) h3.attribute("ADD_DATE").value else null,
            if (h3.attribute("LAST_MODIFIED") != null) h3.attribute("LAST_MODIFIED").value else null,
            processChildren(dl)
        )
    }

    private fun extractNsRoot(h3: Element): String? {
        val attribute = h3.attribute("PERSONAL_TOOLBAR_FOLDER");
        return if (attribute != null && attribute.hasDeclaredValue()) "toolbar" else null
    }


    private fun processChildren(dl: Element): List<NetScapeBookmarkNode> {
        val bookmarks = dl
            .children()
            .toList()
            .filter { it.tagName().lowercase() == "a" && it.previousElementSibling().tagName().lowercase() == "dt" }
            .map(this::parseBookmark)

        val folders = dl
            .children()
            .toList()
            .filter { it.tagName().lowercase() == "dl" }
            .map(this::processFolder)

        return bookmarks + folders
    }

    private fun parseBookmark(dt: Element): Bookmark {
        val a = dt
            .children()
            .first { it.tagName().lowercase() == "a" }

        return Bookmark(
            a.text(),
            if (a.attribute("ADD_DATE") != null) a.attribute("ADD_DATE").value else null,
            if (a.attribute("LAST_MODIFIED") != null) a.attribute("LAST_MODIFIED").value else null,
            if (a.attribute("HREF") != null) a.attribute("HREF").value else null,
            if (a.attribute("ICON") != null) a.attribute("ICON").value else null,
            if (a.attribute("ICON_URI") != null) a.attribute("ICON_URI").value else null
        )
    }
}