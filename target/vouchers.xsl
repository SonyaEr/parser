<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:smv="http://silentium.ua/entity/vouchers"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" doctype-public="html" />
	<xsl:template match="smv:VoucherList">
		<html>
			<head>
				<link rel="stylesheet" type="text/css" href="style.css" />
				<link crossorigin="anonymous"
					href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
					integrity="sha384-
Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
					rel="stylesheet" />
			</head>
			<body>
				<h6 class="text-success" align="center">
					<b>
						<font size="8" face="monospace">Список путевок</font>
					</b>
				</h6>
				<div class="card-body mt-2">
					<table class="table table-bordered" width="100%">
						<thead class="thead-dark">

							<tr>
								<th width="4%">
									<font size="5" color="white" face="monospace">Номер путевки</font>
								</th>
								<th width="4%">
									<font size="5" color="white" face="monospace">Номер заказа</font>
								</th>
								<th width="23%">
									<font size="5" color="white" face="monospace">Клиент</font>
								</th>
								<th width="23%">
									<font size="5" color="white" face="monospace">Тур</font>
								</th>
								<th width="8%">
									<font size="5" color="white" face="monospace">Оплата</font>
								</th>
								<th width="10%">
									<font size="5" color="white" face="monospace">Способ связи</font>
								</th>
								<th width="12%">
									<font size="5" color="white" face="monospace">Менеджер</font>
								</th>
								<th width="7%">
									<font size="5" color="white" face="monospace">Статус</font>
								</th>
								<th width="9%">
									<font size="5" color="white" face="monospace">Обновление заявки</font>
								</th>
							</tr>
						</thead>
						<xsl:for-each select="smv:Voucher">
							<xsl:apply-templates select="." />
						</xsl:for-each>
					</table>
				</div>
				<h4 class="text-danger" align="center">
					<font size="8" face="monospace">Фильтрация путевок по менеджеру</font>
				</h4>
				<xsl:call-template name="filter" />

			</body>
		</html>
	</xsl:template>
	<xsl:template name="Voucher" match="smv:Voucher">
		<tr>
			<td>
				<xsl:value-of select="@id" />
			</td>
			<xsl:apply-templates select="smv:Order" />
			<td><p>Сумма:
			<xsl:value-of select="smv:amount" /> евро </p>
			<p>Кол-во туристов:
			<xsl:value-of select="smv:voucher_count" /></p>
			</td>
			<xsl:apply-templates select="smv:Order" mode = "contact" />
			
			<td>
				<xsl:apply-templates mode="manager"
					select="smv:Person" />
			</td>
		
			<td>
				<xsl:value-of select="smv:status_voucher" />
			</td>
			<td>
				<xsl:value-of select="smv:date_updated_voucher" />
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="Order" match="smv:Order">
		<td>
			<xsl:value-of select="@id" />
		</td>
		<td>
			<xsl:apply-templates mode="common"
				select="smv:Person" />
			<ul class="list">
				<li>
					Кол-во туристов в заказе:
					<xsl:value-of select="smv:order_count" />
				</li>
			</ul>
		</td>
		<td>
			<xsl:apply-templates select="smv:Tour_date" />
		</td>
	</xsl:template>
	
	<xsl:template name="Order1" match="smv:Order"
		mode="contact">
		<td>
			<xsl:apply-templates select="smv:Person"
				mode="contact" />
			Связаться по
			<xsl:value-of select="smv:order_contact" />
		</td>
		</xsl:template>

	<xsl:template name="Person" match="smv:Person"
		mode="common">

		<ul class="list">
			<li>
				ID:
				<xsl:value-of select="@id" />
			</li>
			<li>
				Имя:
				<xsl:value-of select="smv:name" />
			</li>
			<li>
				Фамилия:
				<xsl:value-of select="smv:surname" />
			</li>
			<li>
				Отчество:
				<xsl:value-of select="smv:patronymic" />
			</li>
			<li>
				Адресс:
				<xsl:value-of select="smv:address" />
			</li>
			<li>
				Дата рождения:
				<xsl:value-of select="smv:birth_date" />
			</li>
			<li>
				Документ:
				<xsl:value-of select="smv:document" />
			</li>
			<li>
				<xsl:value-of select="smv:email" />
			</li>
		</ul>
	</xsl:template>

	<xsl:template name="Person1" match="smv:Person"
		mode="contact">
		<p>
			Телефон: +
			<xsl:value-of select="smv:tel_num" />
		</p>

		<p>
			Почта:
			<xsl:value-of select="smv:email" />
		</p>
	</xsl:template>
	<xsl:template name="Manager" match="smv:Person"
		mode="manager">
		<p>
			ID Менеджера:
			<xsl:value-of select="@id" />
		</p>
		<p>
			Имя:
			<xsl:value-of select="smv:name" />
		</p>
		<p>
			Фамилия:
			<xsl:value-of select="smv:surname" />
		</p>
		<p>
			Телефон: +
			<xsl:value-of select="smv:tel_num" />
		</p>

		<p>
			Почта:
			<xsl:value-of select="smv:email" />
		</p>


	</xsl:template>
	<xsl:template name="Tour_date" match="smv:Tour_date">
		<ul class="list">
			<li>
				ID:
				<xsl:value-of select="@id" />
			</li>
			<li>
				Цена:
				<xsl:value-of select="smv:price" /> евро
			</li>
			<li>
				Дата отправления:
				<xsl:value-of select="smv:date_arrival" />
			</li>
		</ul>
		<xsl:apply-templates select="smv:Tour" />

	</xsl:template>
	<xsl:template name="Tour" match="smv:Tour">
		<ul class="list">
			<li>
				Имя:
				<xsl:value-of select="smv:tour_name" />
			</li>
			<li>
				Описание:
				<xsl:value-of select="smv:tour_description" />
			</li>
			<li>
				Количество ночей:
				<xsl:value-of select="smv:quantity_night" />
			</li>
			<li>
				Тур оператор
				<xsl:value-of select="smv:tour_operator" />
			</li>
			<li>
				Питание:
				<xsl:value-of select="smv:type_food" />
			</li>
			<li>
				Транспорт:
				<xsl:value-of select="smv:type_transport" />
			</li>
			<li>
				Тип тура:
				<xsl:value-of select="smv:type_tour" />
			</li>
		</ul>
	</xsl:template>
	<xsl:template name="filter">
		<div class="card-body mt-2">
					<table class="table table-bordered" width="100%">
						<thead class="thead-dark">

							<tr>
								<th width="4%">
									<font size="5" color="white" face="monospace">Номер путевки</font>
								</th>
								<th width="4%">
									<font size="5" color="white" face="monospace">Номер заказа</font>
								</th>
								<th width="23%">
									<font size="5" color="white" face="monospace">Клиент</font>
								</th>
								<th width="23%">
									<font size="5" color="white" face="monospace">Тур</font>
								</th>
								<th width="8%">
									<font size="5" color="white" face="monospace">Оплата</font>
								</th>
								<th width="10%">
									<font size="5" color="white" face="monospace">Способ связи</font>
								</th>
								<th width="12%">
									<font size="5" color="white" face="monospace">Менеджер</font>
								</th>
								<th width="7%">
									<font size="5" color="white" face="monospace">Статус</font>
								</th>
								<th width="9%">
									<font size="5" color="white" face="monospace">Обновление заявки</font>
								</th>
							</tr>
						</thead>
				<xsl:for-each
					select="smv:Voucher[smv:Person[@id='3']]"> 
					<xsl:sort select="@id" order="descending" />
					<xsl:apply-templates select="." />
				</xsl:for-each>

			</table>
		</div>
	</xsl:template>
</xsl:stylesheet>
