<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>

  <xsl:param name="pageId" />
  <xsl:param name="indexPage"/>

  <xsl:template match="section" mode="topmenu">
    <a>
      <xsl:attribute name="href">#<xsl:value-of select="string(@id)" /></xsl:attribute>
      <xsl:value-of select="string(@shortname)" />
    </a>
    <xsl:if test="position() != last()">
      <span style="padding:0px 5px;"><xsl:text disable-output-escaping="yes"><![CDATA[&middot;]]></xsl:text></span>
    </xsl:if>
  </xsl:template>
  
  <xsl:template match="ul" mode="content">
    <xsl:copy-of select="." />
  </xsl:template>

  <xsl:template match="p" mode="content">
    <p>
      <span class="c-num">0<xsl:value-of select="1 + count(preceding-sibling::p)" /></span>
      <xsl:copy-of select="node()" />
    </p>
  </xsl:template>

  <xsl:template match="section" mode="content">
    <a href="#">
      <xsl:attribute name="name"><xsl:value-of select="string(@id)"/></xsl:attribute>
      <xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
    </a>
    <span class="c-title"><xsl:value-of select="string(@fullname)" /> <a href="#">наверх <xsl:text disable-output-escaping="yes"><![CDATA[&uarr;]]></xsl:text></a></span>
    <div class="clear"><xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text></div>
    <div class="text-column">
      <xsl:apply-templates mode="content"/>
    </div>
  </xsl:template>

  <xsl:template match="page">
    <div class="clear" style="padding-bottom:16px; text-align:left;">
      <xsl:apply-templates select="section" mode="topmenu" />
    </div>
    <xsl:apply-templates select="section" mode="content" />
  </xsl:template>
    
  <xsl:template match="/root">
    <xsl:apply-templates select="pages/page[string(@id) = string($pageId)]" />
  </xsl:template>
</xsl:stylesheet>