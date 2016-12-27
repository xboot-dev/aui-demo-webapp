/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.AdminGadgetPanel;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.DashboardGadgetPanel;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.DashboardGadgetPanelManager;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.admin.AccountsGadget;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.admin.GeneralGadget;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.admin.PluginsGadget;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.dashboard.DeviceGadget;

/**
 * Default Portal Configuration class
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class DefaultPortalConfig extends AbstractPortalConfig {

	private static final String ADMIN_NAVIGATION_GENERAL = PortalNavigationSystem.ADMIN_NAVIGATION + "/general";
	private static final String ADMIN_NAVIGATION_ACCOUNTS = PortalNavigationSystem.ADMIN_NAVIGATION + "/accounts";
	private static final String ADMIN_NAVIGATION_PLUGINS = PortalNavigationSystem.ADMIN_NAVIGATION + "/plugins";
	private static final String TOP_BAR_REPORTS = PortalNavigationSystem.NAVIGATION_TOP_BAR + "/reports";
	private static final String TOP_BAR_PAGES = PortalNavigationSystem.NAVIGATION_TOP_BAR + "/pages";
	private static final String DASHBOARD_HEADER_ACTIONS = "dashboard.header.actions";
	private static final String REPORT_HEADER_ACTIONS = "report.header.actions";

	@Override
	public void configure() {
		setPortalCopyright(getCopyrightHtml());

		createNavigation();
		createPortalResources();
		createAdminGadgets();
		createDashboard();
	}

	protected String getCopyrightHtml() {
		String html = "Copyright &copy; 2016 <a href=\"http://tomas.hrdlicka.co.uk\" title=\"Tomas Xboot Hrdlicka\">Tomas Hrdlicka</a>";
		return html;
	}

	protected void createNavigation() {
		log.debug("Creating portal navigation...");

		createNavigationAppSwitcher();
		createNavigationMain();
		createNavigationHeader();
		createNavigationHelp();
		createNavigationFooter();
		createNavigationAdmin();
		createNavigationDashboard();
		createNavigationReport();
		createNavigationPages();
	}

	protected void createNavigationAppSwitcher() {
		// Application Switcher Navigation
		NavigationSection nsAppsSwitcher =
				NavigationSection.createSection(
						PortalNavigationSystem.NAVIGATION_APPSWITCHER,
						"nsys-portal_appswitcher",
						"Application Switcher");
		getPortalNavigation().addSection(nsAppsSwitcher);

		NavigationItem niJira = new NavigationItem();
		niJira.setKey("nsys-portal_appswitcher_jira");
		niJira.setLabel("JIRA");
		niJira.setLink("https://jira.nsys.org");
		niJira.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_APPSWITCHER, niJira);

		NavigationItem niConfluence = new NavigationItem();
		niConfluence.setKey("nsys-portal_appswitcher_confluence");
		niConfluence.setLabel("Confluence");
		niConfluence.setLink("http://confluence.nsys.org");
		niConfluence.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_APPSWITCHER, niConfluence);

		NavigationItem niTeamCity = new NavigationItem();
		niTeamCity.setKey("nsys-portal_appswitcher_teamcity");
		niTeamCity.setLabel("TeamCity");
		niTeamCity.setLink("https://teamcity.nsys.org");
		niTeamCity.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_APPSWITCHER, niTeamCity);
	}

	protected void createNavigationMain() {
		// Main navigation
		NavigationSection nsNavigationBar =
				NavigationSection.createSection(
						PortalNavigationSystem.NAVIGATION_TOP_BAR,
						"nsys-portal_navigation-bar",
						"Main Navigation Bar");
		getPortalNavigation().addSection(nsNavigationBar);

		NavigationSection nsGetStarted =
				NavigationSection.createSection(
						"system.top.navigation.bar/getstarted",
						"nsys-portal_nav-main_getstarted",
						"Get Started");
		getPortalNavigation().addSection(nsGetStarted);

		NavigationItem niOverview = new NavigationItem();
		niOverview.setKey("nsys-portal_nav-main_getstarted_overview");
		niOverview.setLabel("Nsys Overview");
		niOverview.setLink("http://www.nsys.org");
		niOverview.setWeight(0);
		getPortalNavigation().addItem(nsGetStarted, niOverview);

		NavigationSection nsDasboards =
				NavigationSection.createSection(
						"system.top.navigation.bar/dashboards",
						"nsys-portal_nav-main_dashboards",
						"Dashboards");
		nsDasboards.setDescription("View and manage your dashboards");
		getPortalNavigation().addSection(nsDasboards);

		NavigationItem niDashboardView = new NavigationItem();
		niDashboardView.setKey("nsys-portal_nav-main_dashboards_view");
		niDashboardView.setLabel("View System Dashboard");
		niDashboardView.setLink("/dashboard");
		niDashboardView.setWeight(0);
		getPortalNavigation().addItem(nsDasboards, niDashboardView);

		NavigationItem niDashboardManage = new NavigationItem();
		niDashboardManage.setKey("nsys-portal_nav-main_dashboards_manage");
		niDashboardManage.setLabel("Manage Dashboards");
		niDashboardManage.setLink("/dashboard/manage");
		niDashboardManage.setWeight(0);
		getPortalNavigation().addItem(nsDasboards, niDashboardManage);
	}

	protected void createNavigationHeader() {
		// Header navigation
		NavigationSection nsHeaderLinks =
				NavigationSection.createSection(
						PortalNavigationSystem.NAVIGATION_HEADER_LINKS,
						"nsys-portal_header-links",
						"Header Links");
		getPortalNavigation().addSection(nsHeaderLinks);

		NavigationSection nsHelpLinks =
				NavigationSection.createSection(
						PortalNavigationSystem.NAVIGATION_HELP_LINKS,
						"nsys-portal_header-links_help",
						"Help Links");
		nsHelpLinks.addParam("iconClass", "aui-icon aui-icon-small aui-iconfont-help");
		getPortalNavigation().addSection(nsHelpLinks);

		NavigationSection nsAdminLinks =
				NavigationSection.createSection(
						PortalNavigationSystem.NAVIGATION_ADMIN_LINKS,
						"nsys-portal_header-links_admin",
						"Administration Links");
		nsAdminLinks.addParam("iconClass", "aui-icon aui-icon-small aui-iconfont-configure");
		getPortalNavigation().addSection(nsAdminLinks);

		NavigationSection nsUserProfileLinks =
				NavigationSection.createSection(
						PortalNavigationSystem.NAVIGATION_USER_PROFILE_LINKS,
						"nsys-portal_header-links_user",
						"User Profile Links");
		nsUserProfileLinks.addParam("iconClass", "has-avatar");
		getPortalNavigation().addSection(nsUserProfileLinks);

		NavigationItem niLogin = new NavigationItem();
		niLogin.setKey("nsys-portal_header-links_login");
		niLogin.setLabel("Login");
		niLogin.setLink("/auth/login");
		niLogin.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_HEADER_LINKS, niLogin);

		NavigationItem niAdmin = new NavigationItem();
		niAdmin.setKey("nsys-portal_header-links_admin");
		niAdmin.setLabel("Administration");
		niAdmin.setLink("/admin");
		niAdmin.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_ADMIN_LINKS, niAdmin);

		NavigationItem niUpm = new NavigationItem();
		niUpm.setKey("nsys-portal_header-links_manage-plugins");
		niUpm.setLabel("Add-ons");
		niUpm.setLink("/admin");
		niUpm.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_ADMIN_LINKS, niUpm);

		NavigationItem niUserMgmt = new NavigationItem();
		niUserMgmt.setKey("nsys-portal_header-links_manage-users");
		niUserMgmt.setLabel("User Management");
		niUserMgmt.setLink("/admin");
		niUserMgmt.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_ADMIN_LINKS, niUserMgmt);

		NavigationItem niUserProfile = new NavigationItem();
		niUserProfile.setKey("nsys-portal_header-links_userprofile");
		niUserProfile.setLabel("User Profile");
		niUserProfile.setLink("/admin");
		niUserProfile.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_USER_PROFILE_LINKS, niUserProfile);

		NavigationItem niLogout = new NavigationItem();
		niLogout.setKey("nsys-portal_header-links_logout");
		niLogout.setLabel("Logout");
		niLogout.setLink("/auth/logout");
		niLogout.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_USER_PROFILE_LINKS, niLogout);
	}

	protected void createNavigationHelp() {
		// Help navigation
		NavigationItem niNsysPlatform = new NavigationItem();
		niNsysPlatform.setKey("nsys-portal_header-links_help_nsys-platform");
		niNsysPlatform.setLabel("Nsys Platform");
		niNsysPlatform.setLink("http://doc.nsys.org/display/NSYS");
		niNsysPlatform.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_HELP_LINKS, niNsysPlatform);

		NavigationItem niNsysDaemon = new NavigationItem();
		niNsysDaemon.setKey("nsys-portal_header-links_help_nsys-daemon");
		niNsysDaemon.setLabel("Nsys Daemon");
		niNsysDaemon.setLink("http://doc.nsys.org/display/NSYS/Nsys+Daemon");
		niNsysDaemon.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_HELP_LINKS, niNsysDaemon);

		NavigationItem niNsysPortal = new NavigationItem();
		niNsysPortal.setKey("nsys-portal_header-links_help_nsys-portal");
		niNsysPortal.setLabel("Nsys Portal");
		niNsysPortal.setLink("http://doc.nsys.org/display/NSYS/Nsys+Portal");
		niNsysPortal.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_HELP_LINKS, niNsysPortal);

		NavigationItem niNsysTerminal = new NavigationItem();
		niNsysTerminal.setKey("nsys-portal_header-links_help_nsys-terminal");
		niNsysTerminal.setLabel("Nsys Terminal");
		niNsysTerminal.setLink("http://doc.nsys.org/display/NSYS/Nsys+Terminal");
		niNsysTerminal.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_HELP_LINKS, niNsysTerminal);

		NavigationItem niNsysApi = new NavigationItem();
		niNsysApi.setKey("nsys-portal_header-links_help_nsys-api");
		niNsysApi.setLabel("Nsys Remote API Reference");
		niNsysApi.setLink("http://doc.nsys.org/display/NSYS/Nsys+Remote+API+Reference");
		niNsysApi.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_HELP_LINKS, niNsysApi);
	}

	protected void createNavigationFooter() {
		// Footer navigation
		NavigationSection nsFooterLinks =
				NavigationSection.createSection(
						PortalNavigationSystem.NAVIGATION_FOOTER_LINKS,
						"nsys-portal_footer-links",
						"Footer Links");
		getPortalNavigation().addSection(nsFooterLinks);

		NavigationItem niIssues = new NavigationItem();
		niIssues.setKey("nsys-portal_footer-links_issues");
		niIssues.setLabel("Issues");
		niIssues.setLink("https://jira.nsys.org/browse/NSYS");
		niIssues.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_FOOTER_LINKS, niIssues);

		NavigationItem niBuilds = new NavigationItem();
		niBuilds.setKey("nsys-portal_footer-links_builds");
		niBuilds.setLabel("Builds");
		niBuilds.setLink("https://teamcity.nsys.org");
		niBuilds.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_FOOTER_LINKS, niBuilds);

		NavigationItem niDocumentation = new NavigationItem();
		niDocumentation.setKey("nsys-portal_footer-links_doc");
		niDocumentation.setLabel("Documentation");
		niDocumentation.setLink("http://doc.nsys.org/display/NSYS");
		niDocumentation.setWeight(0);
		getPortalNavigation().addItem(PortalNavigationSystem.NAVIGATION_FOOTER_LINKS, niDocumentation);
	}

	protected void createNavigationAdmin() {
		// Administration navigation
		NavigationSection nsAdminNavigation =
				NavigationSection.createSection(
						PortalNavigationSystem.ADMIN_NAVIGATION,
						"nsys-portal_admin-navigation",
						"Administration Navigation");
		getPortalNavigation().addSection(nsAdminNavigation);

		NavigationSection nsAdminNavigationGeneral =
				NavigationSection.createSection(
						ADMIN_NAVIGATION_GENERAL,
						"nsys-portal_admin-general",
						"General");
		getPortalNavigation().addSection(nsAdminNavigationGeneral);

		NavigationSection nsAdminNavigationAccounts =
				NavigationSection.createSection(
						ADMIN_NAVIGATION_ACCOUNTS,
						"nsys-portal_admin-accounts",
						"Accounts");
		getPortalNavigation().addSection(nsAdminNavigationAccounts);

		NavigationSection nsAdminNavigationPlugins =
				NavigationSection.createSection(
						ADMIN_NAVIGATION_PLUGINS,
						"nsys-portal-upm_admin-plugins",
						"Add-ons");
		getPortalNavigation().addSection(nsAdminNavigationPlugins);

		NavigationItem niOverview = new NavigationItem();
		niOverview.setKey("nsys-portal_admin-general-overview");
		niOverview.setLabel("Overview");
		niOverview.setLink("/admin");
		niOverview.setWeight(0);
		getPortalNavigation().addItem(ADMIN_NAVIGATION_GENERAL, niOverview);

		NavigationItem niSystemInfo = new NavigationItem();
		niSystemInfo.setKey("nsys-portal_admin-general-system-info");
		niSystemInfo.setLabel("System Info");
		niSystemInfo.setLink("/admin/system-info");
		niSystemInfo.setWeight(0);
		getPortalNavigation().addItem(ADMIN_NAVIGATION_GENERAL, niSystemInfo);

		NavigationItem niLicensing = new NavigationItem();
		niLicensing.setKey("nsys-portal_admin-general-licensing");
		niLicensing.setLabel("Licensing");
		niLicensing.setLink("/admin");
		niLicensing.setWeight(0);
		getPortalNavigation().addItem(ADMIN_NAVIGATION_GENERAL, niLicensing);

		NavigationItem niConfiguration = new NavigationItem();
		niConfiguration.setKey("nsys-portal_admin-general-admin-console");
		niConfiguration.setLabel("Node Admin Console");
		niConfiguration.setLink("/admin");
		niConfiguration.setWeight(0);
		getPortalNavigation().addItem(ADMIN_NAVIGATION_GENERAL, niConfiguration);

		NavigationItem niUsers = new NavigationItem();
		niUsers.setKey("nsys-portal_admin-accounts-users");
		niUsers.setLabel("Users");
		niUsers.setLink("/admin");
		niUsers.setWeight(0);
		getPortalNavigation().addItem(ADMIN_NAVIGATION_ACCOUNTS, niUsers);

		NavigationItem niFindAddons = new NavigationItem();
		niFindAddons.setKey("nsys-portal-upm_find-new-plugins");
		niFindAddons.setLabel("Find new add-ons");
		niFindAddons.setLink("/admin");
		niFindAddons.setWeight(0);
		getPortalNavigation().addItem(ADMIN_NAVIGATION_PLUGINS, niFindAddons);

		NavigationItem niManageAddons = new NavigationItem();
		niManageAddons.setKey("nsys-portal-upm_manage-plugins");
		niManageAddons.setLabel("Manage add-ons");
		niManageAddons.setLink("/admin");
		niManageAddons.setWeight(0);
		getPortalNavigation().addItem(ADMIN_NAVIGATION_PLUGINS, niManageAddons);
	}

	protected void createNavigationDashboard() {
		// Navigation dashboard.header.actions
		NavigationSection nsDashboardActions =
				NavigationSection.createSection(
						DASHBOARD_HEADER_ACTIONS,
						"nsys-portal_dashboard-header-actions",
						"Dashboard Header Actions");
		getPortalNavigation().addSection(nsDashboardActions);

		NavigationItem niConfiguration = new NavigationItem();
		niConfiguration.setKey("nsys-portal_dashboard-header-actions_conf");
		niConfiguration.setLabel("Configuration");
		niConfiguration.setLink("/admin/configuration");
		niConfiguration.setWeight(0);
		getPortalNavigation().addItem(DASHBOARD_HEADER_ACTIONS, niConfiguration);

		NavigationItem niAuditLog = new NavigationItem();
		niAuditLog.setKey("nsys-portal_dashboard-header-actions_auditlog");
		niAuditLog.setLabel("Audit Log");
		niAuditLog.setLink("/admin/auditlog");
		niAuditLog.setWeight(0);
		niAuditLog.setStyleClass("portal-dialog portal-dialog-submit");
		getPortalNavigation().addItem(DASHBOARD_HEADER_ACTIONS, niAuditLog);
	}

	protected void createNavigationReport() {
		// Report navigation
		NavigationSection nsReportNavigation =
				NavigationSection.createSection(
						PortalNavigationSystem.REPORT_NAVIGATION,
						"nsys-portal_report-navigation",
						"Report Navigation");
		getPortalNavigation().addSection(nsReportNavigation);

		NavigationSection nsTopBarReports =
				NavigationSection.createSection(
						TOP_BAR_REPORTS,
						"nsys-portal_nav-main_reports",
						"Reports");
		getPortalNavigation().addSection(nsTopBarReports);

		NavigationItem niSampleReport = new NavigationItem();
		niSampleReport.setKey("nsys-portal_nav-main_reports_sample");
		niSampleReport.setLabel("Sample Report");
		niSampleReport.setLink("/report/sample");
		niSampleReport.setWeight(0);
		getPortalNavigation().addItem(TOP_BAR_REPORTS, niSampleReport);

		// Navigation report.header.actions
		NavigationSection nsReportActions =
				NavigationSection.createSection(
						REPORT_HEADER_ACTIONS,
						"nsys-portal_report-header-actions",
						"Report Header Actions");
		getPortalNavigation().addSection(nsReportActions);

		NavigationItem niExport = new NavigationItem();
		niExport.setKey("nsys-portal_report-header-actions_export");
		niExport.setLabel("Export");
		niExport.setLink("/report/export");
		niExport.setWeight(0);
		getPortalNavigation().addItem(REPORT_HEADER_ACTIONS, niExport);
	}

	protected void createNavigationPages() {
		NavigationSection nsTopBarPages =
				NavigationSection.createSection(
						TOP_BAR_PAGES,
						"nsys-portal_nav-main_pages",
						"Pages");
		getPortalNavigation().addSection(nsTopBarPages);

		NavigationItem niDemoPage = new NavigationItem();
		niDemoPage.setKey("nsys-portal_nav-main_pages_demo");
		niDemoPage.setLabel("Demo Page");
		niDemoPage.setLink("/demo/page");
		niDemoPage.setWeight(0);
		getPortalNavigation().addItem(TOP_BAR_PAGES, niDemoPage);
	}

	protected void createPortalResources() {
		log.debug("Creating portal resources...");

		PortalResource adminPortalResource = new PortalResource();
		adminPortalResource.setKey("nsys-portal_admin-resources");
		adminPortalResource.setName("Administration Web Resources");
		adminPortalResource.setContext("nsys.portal.admin");

		adminPortalResource.addResource(
				PortalResourceItem.create(PortalResourceItem.TYPE_DOWNLOAD, "admin.css", "/resources/css/admin.css"));

		getPortalResourceManager().addResource(adminPortalResource);

		PortalResource projectsPortalResource = new PortalResource();
		projectsPortalResource.setKey("nsys-portal_projects-resources");
		projectsPortalResource.setName("Projects Web Resources");
		projectsPortalResource.setContext("projects");

		projectsPortalResource.addResource(
				PortalResourceItem.create(PortalResourceItem.TYPE_DOWNLOAD, "projects.css", "/resources/css/projects.css"));

		getPortalResourceManager().addResource(projectsPortalResource);

		PortalResource chartPortalResource = new PortalResource();
		chartPortalResource.setKey("nsys-portal_chart-resources");
		chartPortalResource.setName("Chart Resources");
		chartPortalResource.setContext("nsys.portal.chart");

		chartPortalResource.addResource(
				PortalResourceItem.create(PortalResourceItem.TYPE_DOWNLOAD, "rickshaw.min.css", "/resources/chart/rickshaw.min.css"));
		chartPortalResource.addResource(
				PortalResourceItem.create(PortalResourceItem.TYPE_DOWNLOAD, "d3.v3.js", "/resources/chart/d3.v3.js"));
		chartPortalResource.addResource(
				PortalResourceItem.create(PortalResourceItem.TYPE_DOWNLOAD, "rickshaw.min.js", "/resources/chart/rickshaw.min.js"));
		chartPortalResource.addResource(
				PortalResourceItem.create(PortalResourceItem.TYPE_DOWNLOAD, "chart.css", "/resources/css/chart.css"));
		chartPortalResource.addResource(
				PortalResourceItem.create(PortalResourceItem.TYPE_DOWNLOAD, "chart.js", "/resources/js/chart.js"));

		getPortalResourceManager().addResource(chartPortalResource);
	}

	protected void createAdminGadgets() {
		log.debug("Creating admin gadgets...");

		AdminGadgetPanel gadgetPanel = ComponentProvider.getInstance().getComponent(AdminGadgetPanel.class);

		GeneralGadget generalGadget = new GeneralGadget();
		generalGadget.setKey("nsys-portal_admin-gadget_general");
		generalGadget.setDisplayName("General");
		generalGadget.setDescription("Provides an overview for the General gadget.");
		generalGadget.setOrder(0);
		gadgetPanel.addLeftColumnGadget(generalGadget);

		AccountsGadget accountsGadget = new AccountsGadget();
		accountsGadget.setKey("nsys-portal_admin-gadget_accounts");
		accountsGadget.setDisplayName("Accounts");
		accountsGadget.setDescription("Provides an overview for the Accounts gadget.");
		accountsGadget.setOrder(0);
		gadgetPanel.addRightColumnGadget(accountsGadget);

		PluginsGadget pluginsGadget = new PluginsGadget();
		pluginsGadget.setKey("nsys-portal-upm_admin-gadget-plugins");
		pluginsGadget.setDisplayName("Add-ons");
		pluginsGadget.setDescription("Provides an overview for the Accounts gadget.");
		pluginsGadget.setOrder(0);
		gadgetPanel.addLeftColumnGadget(pluginsGadget);
	}

	protected void createDashboard() {
		log.debug("Creating dashboard...");

		DashboardGadgetPanelManager gadgetPanelManager = ComponentProvider.getInstance().getComponent(DashboardGadgetPanelManager.class);

		DashboardGadgetPanel systemDashboard = new DashboardGadgetPanel();
		systemDashboard.setName("System Dashboard");
		systemDashboard.setDescription("The system dashboard provides an overview about system services.");
		systemDashboard.setViewId("system");
		systemDashboard.setImageUri("${contextPath}/resources/images/nsys_logo_avatar.png");
		systemDashboard.setActionButtons(DASHBOARD_HEADER_ACTIONS);
		gadgetPanelManager.addDashboard(systemDashboard.getViewId(), systemDashboard);

		DeviceGadget deviceGadget = new DeviceGadget();
		deviceGadget.setKey("nsys-portal_dashboard-system_devices");
		deviceGadget.setDisplayName("Devices");
		deviceGadget.setDescription("Provides a list of connected devices.");
		deviceGadget.setOrder(0);
		systemDashboard.addLeftColumnGadget(deviceGadget);

		DeviceGadget deviceGadget2 = new DeviceGadget();
		deviceGadget2.setKey("nsys-portal_dashboard-system_devices2");
		deviceGadget2.setDisplayName("Devices");
		deviceGadget2.setDescription("Provides a list of connected devices.");
		deviceGadget2.setOrder(0);
		systemDashboard.addLeftColumnGadget(deviceGadget2);

		DeviceGadget deviceGadget3 = new DeviceGadget();
		deviceGadget3.setKey("nsys-portal_dashboard-system_devices3");
		deviceGadget3.setDisplayName("Devices");
		deviceGadget3.setDescription("Provides a list of connected devices.");
		deviceGadget3.setOrder(0);
		systemDashboard.addLeftColumnGadget(deviceGadget3);

		DeviceGadget deviceGadget4 = new DeviceGadget();
		deviceGadget4.setKey("nsys-portal_dashboard-system_devices4");
		deviceGadget4.setDisplayName("Devices");
		deviceGadget4.setDescription("Provides a list of connected devices.");
		deviceGadget4.setOrder(0);
		systemDashboard.addRightColumnGadget(deviceGadget4);

		DeviceGadget deviceGadget5 = new DeviceGadget();
		deviceGadget5.setKey("nsys-portal_dashboard-system_devices5");
		deviceGadget5.setDisplayName("Devices");
		deviceGadget5.setDescription("Provides a list of connected devices.");
		deviceGadget5.setOrder(0);
		systemDashboard.addRightColumnGadget(deviceGadget5);

		DeviceGadget deviceGadget6 = new DeviceGadget();
		deviceGadget6.setKey("nsys-portal_dashboard-system_devices6");
		deviceGadget6.setDisplayName("Devices");
		deviceGadget6.setDescription("Provides a list of connected devices.");
		deviceGadget6.setOrder(0);
		systemDashboard.addRightColumnGadget(deviceGadget6);
	}
}