<h1>介绍</h1>
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
</ul>
<h1>分层测试</h1>
<P>应用系统分层已经是项目架构方面的共识，针对不同职责将程序分为不同层次，每层只完成特定的任务，即简化应用系统复杂度，又提高应用系统的可维护性。同样，测试也应该针对不同层次采用分层测试，每一层测试用例和测试方法都和应用系统分层对应，让测试用例职责单一化，降低测试用例复杂度和可维护性。</P>
<ul>
<h2>集成测试</h2>
<P>集成测试是测试微服务对外提供的服务，属于功能测试和黑盒测试。集成测试要求微服务是完整状态，包括代码、配置、数据库等。集成测试用例继承BaseAssembleTest基类。在setup方法中将数据库数据环境，保证测试可重复。例如：
<pre>
/**
 * 集成测试样例
 * @author lin.pu
 *
 */
public class SampleControllerTest extends BaseAssembleTest {

	@Before
	public void setUp() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {

		// 加载数据库文本数据
		IDataSet dataSet = getXmlDataSet("sample/init/init_db.xml");
		// 将数据加载到数据库中，采用clean_insert模式（具体模式见dbunit）
		DatabaseOperation.CLEAN_INSERT.execute(getConn(), dataSet);
	}

	@Test	public void testview() throws Exception {
		// 测试普通控制器
		mockMvc.perform(get("/sample/{id}", 21)) // 执行请求
				.andExpect(model().attributeExists("bean")) // 验证存储模型数据
				.andExpect(model().attribute("bean", hasProperty("name", equalTo("zhang")))) // 验证存储模型数据
				.andExpect(view().name("user/view")) // 验证viewName
				.andExpect(forwardedUrl("/WEB-INF/jsp/user/view.jsp"))// 验证视图渲染时forward到的jsp
				.andExpect(status().isOk())// 验证状态码
				.andDo(print()); // 输出MvcResult到控制台
	}

	@Test
	public void testviews() throws Exception {
		// 测试普通控制器
		String json = mockMvc.perform(get("/sample/up/{age}", 18)) // 执行请求
				.andExpect(status().isOk())// 验证状态码
				.andDo(print())// 输出MvcResult到控制台
				.andReturn()// 输出返回json对象
				.getResponse().getContentAsString();
		// 验证测试结果
		List<SampleBean> lst = new ArrayList<SampleBean>();
		SampleBean bean = new SampleBean();
		bean.setId(14L);
		bean.setName("zhang4");
		bean.setAddress("上海浦东东港");
		bean.setPhone("1301238733841");
		bean.setAge(19);
		SampleBean bean1 = new SampleBean();
		bean1.setId(15L);
		bean1.setName("admin5");
		bean1.setAddress("上海浦东");
		bean1.setPhone("130123293840");
		bean1.setAge(18);
		SampleBean bean2 = new SampleBean();
		bean2.setId(21L);
		bean2.setName("zhang");
		bean2.setAddress("上海浦东东港");
		bean2.setPhone("1301238733841");
		bean2.setAge(24);
		lst.add(bean);
		lst.add(bean2);
		lst.add(bean1);
		Assert.isTrue(JsonCompareUtils.jsonEquals(lst, json), "返回结果json对象与预期不一致");
	}

}
</pre>
<p>数据文件样例：<a href="https://github.com/pulin2004/test-framework/blob/master/src/test/resources/testcase/assembly/sample/init/init_db.xml">init_db.xml</a></P>
<h2>service层测试</h2>
<p>service层测试属于单元测试，是针对各个方
<P>测试框架配置</P>
<P>cotroller测试</P>
<P>dao层测试</P>
<P>service层测试</P>
<br/>
<P>基于测试项目管理</P>

  
