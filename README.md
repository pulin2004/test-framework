<h1>介绍</h1>
<P>自动化测试一直是软件项目开发的一个难点，虽然大家能认识到测试自动化的好处，但测试用例编制一直是一个困扰大家难点，阻碍自动化测试在项目中的应用。另外，目前软件项目一般项目把测试看做是项目验收的手段,测试用例只是用在验收测试阶段。其实测试用例用处非常大。</P>
<ul>
<li>测试是软件质量的保障</li>
<li>测试是软件持续进化的保障</li>
<li>测试是软件的使用手册</li>
<li>测试是软件开发管理的重要基础</li>
<li>测试是解决需求、设计、开发沟通的一种方法</li>
<li>降低开发人员对业务理解的门槛</li>
</ul>
<P>随着微服务技术发展，特别是DevOps概念提出，要求开发->测试->部署完全自动化，因此自动化测试成为微服务技术不可或缺的一部分，测试用例贯穿微服务全生命之中，是微服务持续进化的保障。本测试框架主要为满足微服务技术发展要求，实现代码（白盒）和功能（黑盒）测试一体化。</P>
<p>相关文章</p>
<a href="https://github.com/pulin2004/test-framework/blob/master/doc/tdd_manage.md">测试与项目管理</a>
<a href="http://www.cnblogs.com/blsong/p/4602001.html">测试驱动开发实践 - Test-Driven Development</a>
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
<p>service层测试属于单元测试，是针对service层各类的方法进行测试。service层测试主要采用mock进行调用模拟。Service层测试用例继承BaseUnitTest基类。测试样例：</p>
<pre>
/**
 * service层测试用例样例
 * 
 * @author lin.pu
 *
 */
public class SampleServiceTest extends BaseUnitTest {

	/**
	 * 被测试的service对象
	 */
	@InjectMocks
	private SampleService sampleService;
	/**
	 * service对象引用的dao对象，用mock替代
	 */
	@Mock
	private SampleDao sampleDao;

	@Test
	public void testQueryById() {
		// 第一步，对mock对象的行为进行模拟
		SampleBean bean = new SampleBean();
		Long id = 99928L;
		// 先设置预期
		when(sampleDao.queryById(id)).thenReturn(bean);
		// 第二步，调用测试方法
		SampleBean act = sampleService.findById(id);
		// 第三步，断言
		Assert.assertEquals(bean, act);

	}
</pre>
<p>另外service层测试还提供批量测试功能，允许把测试参数和断言写在CSV和XLS文件中，批量进行验证，例如：</P>
<pre>
/**
 * 批量参数测试样例
 * 
 * @author lin.pu
 *
 */
public class SwitchUtilsTest extends BaseUnitTest {

	@Test // Specify the method as a test method
	@Source("/testcase/switch.xls") // 参数文件
	public void testSwitchSample(int i, int b, int expect) {
		Assert.assertEquals(expect, SwitchUtils.SwitchSample(i, b));
	}

}
</pre>
<p>数据文件样例：<a href="https://github.com/pulin2004/test-framework/blob/master/src/test/resources/testcase/switch.xls">switch.xls</a></P>
<h2>dao层测试</h2>
<p>dao层是数据访问层，对该层测试采用连着数据库一起测试，因此dao层测试必须解决数据库环境和数据库数据断言验证。dao层测试用例继承BaseDaoTest基类。测试样例：</p>
<pre>
/**
 * dao层测试样例
 * @author lin.pu
 *
 */
public class SampleDaoTest extends BaseDaoTest{

	@Autowired
	private SampleDao sampleDao;
	
	  @Before
	    public void setUp() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {

	    	//加载数据库文本数据
	        IDataSet dataSet = getXmlDataSet("sample"+File.separator+"init_db.xml");
	        //将数据加载到数据库中
	        DatabaseOperation.CLEAN_INSERT.execute(getConn(),dataSet); 
	    }
	  
	  @Test
	  public void updateTest() throws IOException, SQLException, DatabaseUnitException
	  {
		  //输入参数准备
		  SampleBean bean = new SampleBean();
		   bean.setAddress("广西");
		   bean.setAge(19);
		   bean.setId(13L);
		   bean.setName("name27");
		   bean.setPhone("13982983393");
		   //调用测试方法
		  sampleDao.updateTest(bean);
		  //数据库数据和预期数据文件对比
		  assertDBEquals("sample"+File.separator+"expect_db.xml","sample");
	  }
</pre>
</ul>
<h1>工具类</h1>
<P>为提高测试用例的编制效率，框架提供的一些常用工具。</P>
<ul>
<h2>数据库数据转换为文件</h2>
<p>框架提供将数据库中的数据转为为xml文件功能，首先配置数据库参数：com.test.framework.base.CreateDatasetXml的@ContextHierarchy标签，将数据库配置文件加载。然后继承该类，调用相应方法进行测试，样例：</P>
<pre>
/**
 * 将数据库数据转换为xml文件
 * @author lin.pu
 *
 */
public class CreateDatabaseXmlDataTest extends CreateDatasetXml{
    @Before
    public void init_DB() throws CannotGetJdbcConnectionException, DatabaseUnitException, IOException, SQLException {

    	//加载数据库文本数据
        IDataSet dataSet = getXmlDataSet("assembly/sample/init/init_db2.xml");
        //将数据加载到数据库中
        DatabaseOperation.CLEAN_INSERT.execute(getConn(),dataSet); 
    }
    
    @Test
    public void testCreateXml() throws Exception
    {
    	backupAll(BaseProperty.ROOT_URL+"temp"+File.separator+"all.xml");
    	backupCustom(BaseProperty.ROOT_URL+"temp"+File.separator+"custom.xml","sample","address");
    	QueryBean bean = new QueryBean();
    	bean.addQuery("sample", "select * from sample where age >18");
    	bean.addQuery("address", "select * from address where city = '厦门'");
    	backupQuery(BaseProperty.ROOT_URL+"temp"+File.separator+"query.xml",bean);
    }


}
</pre>
<h2>json对象比较</h2>
<P>框架提供对json对象的比较，你可以比较复杂json对象是否相等，也可以比较具体对象与json对象是否一致。具体见<a href="https://github.com/pulin2004/test-framework/blob/master/src/test/java/com/test/framework/base/JsonCompareUtils.java">JsonCompareUtils.java</a></P>
</ul>

  
