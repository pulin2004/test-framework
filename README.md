#test-framework
<h1>介绍<h1>
<P>自动化测试一直是软件项目开发的一个难点，大家都能认识到测试自动化的好处，但测试用例编制一直是一个难点，阻碍自动化测试在项目中的应用。普遍对自动化测试认识存在误区，认为自动化测试“门槛较高，学习曲线较陡，成本较高”。主要原因是一般项目把测试看做是项目验收的手段,只要系统通过测试，测试用例就不在有价值，编制自动化测试用例费事费力。</P>
<P>随着微服务技术发展，特别是DevOps概念提出，要求开发->测试->部署完全自动化，因此自动化测试成为微服务技术不可或缺的一部分，测试用例贯穿微服务全生命之中，是微服务持续进化的保障。本测试框架就是为满足微服务技术发展要求，实现代码（白盒）和功能（黑盒）测试一体化。</P>
<h1>配置</h1>
<ul>
<li>测试框架基于junit测试框架，集成spring test、dbunit、mockito、feed4junit等多个测试组件，把框架pom.xml中的<scope>test</scope>的配置拷贝到项目中。</li>
<li>配置测试用例文件路径,直接修改com.test.framework.base.BaseProperty.java 类中配置，注意TestCase的目录配置项目测试目录中，统一进行版本管理。</li>
<pre>
	/**
	 * 测试用例根目录
	 */
	public static final String ROOT_URL = System.getProperty("user.dir") +File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"testcase"+File.separator;
	/**
	 * 集成测试用例目录
	 */
	public static final String ASSEMBLY_URL = ROOT_URL+"assembly"+File.separator;
	/**
	 * dao测试用例目录
	 */
	public	static final String DAO_URL = ROOT_URL+"dao"+File.separator;
</pre>
<li>spring配置文件路径,分别修改com.test.framework.base包下BaseAssembleTest、BaseDaoTest、BaseUnitTest类的注解@ContextHierarchy，将spring配置文件路径全部加载,例如：
</li>
<pre>
@ContextHierarchy({ 
	@ContextConfiguration("classpath:spring/spring-db.xml"),
	@ContextConfiguration("classpath:spring/spring-config.xml"),
	@ContextConfiguration("classpath:mvc/spring-mvc.xml")
})
</pre>
<li>配置web.xml文件加载路径，修改com.test.framework.base包下的BaseAssembleTest的注解@WebAppConfiguration，例如：
</li>
<pre>
@WebAppConfiguration(value = "test.framwork/src/main/webapp")
</pre>
<hi>分层测试</hi>
<P>应用系统分层已经是项目架构方面的共识，针对不同职责将程序分为不同层次，每层只完成特定的任务，即简化应用系统复杂度，又提高应用系统的可维护性。同样，测试也应该针对不同层次采用分层测试，每一层测试用例和测试方法都和应用系统分层对应，让测试用例职责单一化，降低测试用例复杂度和可维护性。</P>
<h2>集成测试</h2>
<P>    自动化测试首先是测试用例编制，测试用例应具备三要素：测试初始环境（初始数据，外部引用，测试类组装），测试方法调用，结果断言。
<P>测试框架配置</P>
<P>cotroller测试</P>
<P>dao层测试</P>
<P>service层测试</P>
<br/>
<P>基于测试项目管理</P>

  
