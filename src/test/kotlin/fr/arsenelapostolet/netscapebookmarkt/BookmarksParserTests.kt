package fr.arsenelapostolet.netscapebookmarkt

import fr.arsenelapostolet.netscapebookmarkt.nodes.NetScapeBookmarkNode
import fr.arsenelapostolet.netscapebookmarkt.nodes.Serializer
import kotlinx.serialization.encodeToString
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
        val result = target.parse(this.javaClass.getResource("/firefox_bookmarks.html").path)
        
        // Then
        assertEquals(firefoxJson, Serializer.jsonSerializer.encodeToString(result));
    }

    @Test
    fun parse_whenChrome_returnsCorrectResult(){
        // Given
        val chromeJson = this.javaClass.getResource("/chrome_bookmarks.json").readText()
        val chromeTestData = Json.decodeFromString<List<NetScapeBookmarkNode>>(chromeJson)
        val target = BookmarksParser()

        // When
        val result = target.parse(this.javaClass.getResource("/chrome_bookmarks.html").toExternalForm())

        // Then
        assertEquals(result, chromeTestData);
    }
}