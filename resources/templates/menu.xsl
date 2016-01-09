<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>

  <xsl:param name="pageId" />
  <xsl:param name="indexPage" />

  <xsl:template match="a">
    <div class="ui-button">
      <a onclick="">
        <xsl:attribute name="href">
          <xsl:choose>
            <xsl:when test="string(@href) = string($indexPage)">./</xsl:when>
            <xsl:otherwise><xsl:value-of select="string(@href)" /></xsl:otherwise>
          </xsl:choose>
        </xsl:attribute>
        <xsl:attribute name="class">
          <xsl:choose>
            <xsl:when test="string(@href) = string($pageId) and string(@href) != string($indexPage)">ui-button active</xsl:when>
            <xsl:otherwise>ui-button</xsl:otherwise>
          </xsl:choose>
          
        </xsl:attribute>
        <span class="b-middle">
          <xsl:value-of select="text()" />
        </span>
      </a>
    </div>
  </xsl:template>
    
  <xsl:template match="/root">
    <xsl:apply-templates select="head/menu/a" />
  </xsl:template>
</xsl:stylesheet>