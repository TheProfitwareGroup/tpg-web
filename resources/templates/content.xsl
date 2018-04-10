<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" omit-xml-declaration="yes"/>

  <xsl:param name="pageId" />
  <xsl:param name="indexPage"/>
  <xsl:param name="language"/>
  <xsl:param name="staticRoot"/>
  <xsl:param name="hireUsForm"/>

  <xsl:template match="section" mode="topmenu">
    <a>
      <xsl:attribute name="href">#<xsl:value-of select="string(@id)" /></xsl:attribute>
      <xsl:value-of select="string(@shortname)" />
    </a>
    <xsl:if test="position() != last()">
      <span style="padding:0px 5px;"><xsl:text disable-output-escaping="yes">&#183;</xsl:text></span>
    </xsl:if>
  </xsl:template>
  
  <xsl:template match="ul" mode="content">
    <xsl:copy-of select="." />
  </xsl:template>

  <xsl:template match="pre" mode="content">
    <xsl:copy-of select="." />
  </xsl:template>

  <xsl:template match="p" mode="content">
    <p>
      <span class="c-num">0<xsl:value-of select="1 + count(preceding-sibling::p)" /></span>
      <xsl:copy-of select="node()" />
    </p>
  </xsl:template>

  <xsl:template match="hireus" mode="content">
    <p>
      <div style="display:table">
        <div style="display:table-cell;vertical-align:middle">
          <a href="{$hireUsForm}">
            <img height="48" width="48" alt="Google Forms" src="{string($staticRoot)}img/google-forms.png" />
          </a>
        </div>
        <div style="display:table-cell;vertical-align:middle">
          <xsl:choose>
            <xsl:when test="string($language) = 'ru'">
              <xsl:text>Один отличный </xsl:text>
              <a href="https://twitter.com/lazy_frontend">фронтендер</a>
              <xsl:text> подготовил для вас специальную форму, которая позволит нам лучше понять ваши намерения.
              Пожалуйста </xsl:text>
              <a>
                <xsl:attribute name="href">
                  <xsl:value-of select="string($hireUsForm)" />
                </xsl:attribute>
                <xsl:text>заполните ее</xsl:text>
              </a>
              <xsl:text>, чтобы иметь возможность нанять людей релевантных вашей вакансии</xsl:text>
            </xsl:when>
            <xsl:otherwise>
              <xsl:text>Feel free to </xsl:text>
              <a>
                <xsl:attribute name="href"><xsl:value-of select="string($hireUsForm)" /></xsl:attribute>
                <xsl:text>fill out this form</xsl:text>
              </a>
            </xsl:otherwise>
          </xsl:choose>
          <xsl:text>.</xsl:text>
        </div>
      </div>
    </p>
  </xsl:template>

  <xsl:template match="section" mode="content">
    <a href="#">
      <xsl:attribute name="name"><xsl:value-of select="string(@id)"/></xsl:attribute>
      <xsl:text disable-output-escaping="yes"><![CDATA[ ]]></xsl:text>
    </a>
    <span class="c-title"><xsl:value-of select="string(@fullname)" /> <a href="#">
    <xsl:choose>
      <xsl:when test="string($language) = 'ru'">наверх </xsl:when>
      <xsl:otherwise>up </xsl:otherwise>
    </xsl:choose>
    <xsl:text disable-output-escaping="yes">&#8593;</xsl:text></a></span>
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
