# somic 增加数据库读写分离配置   aop通知需要使用代理类才会去插入增强处理逻辑
  <bean id="dataSourceAdvice" class="cc.s2m.web.utils.webUtils.dataSource.DataSourceAdvice"></bean>
	<bean name="dataSourceSwitcher" class="org.springframework.aop.support.JdkRegexpMethodPointcut" scope="prototype">
		<property name="patterns">
			<list>
				<value>com.yinmimoney.web.p2pnew.service.*</value>
			</list>
		</property>
	</bean>
	<aop:config  proxy-target-class="true">
        <!-- 在切入点处插入增强处理、完成"织入" -->
        <aop:advisor advice-ref="dataSourceAdvice" pointcut-ref="dataSourceSwitcher"/>
    </aop:config>
