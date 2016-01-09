<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>

  <xsl:param name="pageId" />

  <xsl:template match="a">
    <xsl:if test="string(@href) = string($pageId)">
      <xsl:value-of select="text()" />
    </xsl:if>
  </xsl:template>
    
  <xsl:template match="/root">
    <xsl:apply-templates select="head/menu/a" />
    ::
    <xsl:value-of select="head/title/text()" />
  </xsl:template>
</xsl:stylesheet>