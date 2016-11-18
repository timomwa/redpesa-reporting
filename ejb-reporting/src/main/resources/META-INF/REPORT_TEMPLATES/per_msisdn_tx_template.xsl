<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo">
	<xsl:template match="transactions">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simpleA4"
					page-height="29.7cm" page-width="21cm" margin-top="2cm"
					margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-size="17pt" font-weight="bold" space-after="5mm">
						Per msisdn Transactions Report. Query->
						<xsl:value-of select="searchString" />
					</fo:block>
					<fo:block font-size="10pt">
						<fo:table table-layout="fixed" width="100%"
							border-collapse="separate">
							<fo:table-column column-width="1cm" />
							<fo:table-column column-width="6cm" />
							<fo:table-column column-width="5cm" />
							<fo:table-column column-width="3cm" />

							<fo:table-header text-align="center"
								background-color="#B4C3C3">
								<fo:table-row>
									<fo:table-cell padding="0.5mm" border-width="0.5mm"
										border-style="solid">
										<fo:block>No.</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="0.5mm" border-width="0.5mm"
										border-style="solid">
										<fo:block>Date</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="0.5mm" border-width="0.5mm"
										border-style="solid">
										<fo:block>Msisdn</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="0.5mm" border-width="0.5mm"
										border-style="solid">
										<fo:block>Transactions</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-header>

							<fo:table-body>
								<xsl:apply-templates select="transaction" />
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="transaction">
		<xsl:variable name="i" select="position()" />
		<xsl:choose>
			<xsl:when test="($i mod 2)=0">
				<xsl:attribute name="background-color">#F0F8FF</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="background-color">#F0FFFF</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		<fo:table-row>
			
			<fo:table-cell padding="0.5mm" border-width="0.5mm"
				border-style="solid">
				<fo:block>
					<xsl:value-of select="concat($i,'. ')" />
				</fo:block>
			</fo:table-cell>

			<fo:table-cell padding="0.5mm" border-width="0.5mm"
				border-style="solid">
				<fo:block>
					<xsl:value-of select="date" />
				</fo:block>
			</fo:table-cell>

			<fo:table-cell padding="0.5mm" border-width="0.5mm"
				border-style="solid" text-align="center">
				<fo:block>
					<xsl:value-of select="msisdn" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding="0.5mm" border-width="0.5mm"
				border-style="solid" text-align="center">
				<fo:block>
					<xsl:value-of select="transactioncount" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
</xsl:stylesheet>