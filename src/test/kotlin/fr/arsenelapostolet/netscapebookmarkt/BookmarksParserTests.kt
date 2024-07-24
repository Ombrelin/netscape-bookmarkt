package fr.arsenelapostolet.netscapebookmarkt

import fr.arsenelapostolet.netscapebookmarkt.nodes.NetScapeBookmarkNode
import fr.arsenelapostolet.netscapebookmarkt.nodes.Serializer
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BookmarksParserTests {
    
    @Test
    fun parse_whenFirefox_returnsCorrectResult(){
        // Given
        val firefoxJson = this.javaClass.getResource("/firefox_bookmarks.json").readText()
        val firefoxTestData = Serializer.jsonSerializer.decodeFromString<List<NetScapeBookmarkNode>>(firefoxJson)
        val target = BookmarksParser()
        
        // When
        val result = target.parse(this.javaClass.getResource("/firefox_bookmarks.html").toExternalForm())
        
        // Then
        assertEquals(result, firefoxTestData);
    }

    @Test
    fun parse_whenChrome_returnsCorrectResult(){
        // Given
        val firefoxJson = this.javaClass.getResource("/chrome_bookmarks.json").readText()
        val firefoxTestData = Json.decodeFromString<List<NetScapeBookmarkNode>>(firefoxJson)
        val target = BookmarksParser()

        // When
        val result = target.parse(this.javaClass.getResource("/chrome_bookmarks.html").toExternalForm())

        // Then
        assertEquals(result, firefoxTestData);
    }
}