package fr.arsenelapostolet.netscapebookmarkt

import fr.arsenelapostolet.netscapebookmarkt.nodes.Bookmark
import fr.arsenelapostolet.netscapebookmarkt.nodes.Folder
import fr.arsenelapostolet.netscapebookmarkt.nodes.NetScapeBookmarkNode
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import kotlin.io.path.Path
import kotlin.io.path.readText

class BookmarksParser {

    fun parse(fileName: String): List<NetScapeBookmarkNode> {
        val text = Path(fileName).readText()

        val document = Jsoup.parse(cleanDocument(text))
        val rootDl = document
            .root()
            .children()
            .first { it.tagName().lowercase() == "html" }
            .children()
            .first { it.tagName().lowercase() == "body" }
            .children()
            .first { it.tagName().lowercase() == "dl" }

        val parseResult = processFolder(rootDl)
        val otherRoots = parseResult.children.filter { it is Folder && it.nsRoot != null }
        return otherRoots + parseResult.copy(children = parseResult.children.minus(otherRoots))
    }

    private fun cleanDocument(text: String): String = text
        .replace("<DT>", "")
        .replace("<p>", "")
        .replace("<DD>", "")

    private fun processFolder(dl: Element): Folder {
        val h = dl
            .previousElementSiblings()
            .first { it.tagName().lowercase().contains("h") }

        if (h == null) {
            throw IllegalStateException("Folder $dl has no title element")
        }

        return Folder(
            h.text(),
            extractNsRoot(h),
            if (h.attribute("ADD_DATE".lowercase()) != null) h.attribute("ADD_DATE".lowercase()).value else null,
            if (h.attribute("LAST_MODIFIED".lowercase()) != null) h.attribute("LAST_MODIFIED".lowercase()).value else null,
            processChildren(dl)
        )
    }

    private fun extractNsRoot(h3: Element): String? {
        val toolbarAttribute = h3.attribute("PERSONAL_TOOLBAR_FOLDER".lowercase());
        if (toolbarAttribute != null && toolbarAttribute.hasDeclaredValue()) {
            return "toolbar";
        }

        val otherAttribute = h3.attribute("UNFILED_BOOKMARKS_FOLDER".lowercase());
        if (otherAttribute != null && otherAttribute.hasDeclaredValue()) {
            return "other_bookmarks";
        }

        return null
    }


    private fun processChildren(dl: Element): List<NetScapeBookmarkNode> {
        val bookmarks = dl
            .children()
            .toList()
            .filter { it.tagName().lowercase() == "a" }
            .map(this::parseBookmark)

        val folders = dl
            .children()
            .toList()
            .filter { it.tagName().lowercase() == "dl" }
            .map(this::processFolder)

        return bookmarks + folders
    }

    private fun parseBookmark(a: Element): Bookmark = Bookmark(
        a.text(),
        if (a.attribute("ADD_DATE".lowercase()) != null) a.attribute("ADD_DATE".lowercase()).value else null,
        if (a.attribute("LAST_MODIFIED".lowercase()) != null) a.attribute("LAST_MODIFIED".lowercase()).value else null,
        if (a.attribute("HREF".lowercase()) != null) a.attribute("HREF".lowercase()).value else null,
        if (a.attribute("ICON".lowercase()) != null) a.attribute("ICON".lowercase()).value else null,
        if (a.attribute("ICON_URI".lowercase()) != null) a.attribute("ICON_URI".lowercase()).value else null,
        if (a.attribute("TAGS".lowercase()) != null) a.attribute("TAGS".lowercase()).value.split(',') else null
    )
}