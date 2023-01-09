import {Route, Routes} from "react-router-dom";
import {page} from "../../constant/page";
import Footer from "../Footer/Footer";
import Header from "../Header/Header";
import AllSpecializationsPage from "../page/admin/specializations/all/AllSpecializationsPage";
import AllSubjectsPage from "../page/admin/subjects/all/AllSubjectsPage";
import AllTestsPage from "../page/admin/tests/all/AllTestsPage";
import HomePage from "../page/home/HomePage";
import LoginPage from "../page/login/LoginPage";
import RegisterPage from "../page/register/RegisterPage";
import CreateTestPage from "../page/admin/tests/create/CreateTestPage";
import AllExamsPage from "../page/admin/exams/all/AllExamsPage";
import ProfilePage from "../page/profile/ProfilePage";
import SettingsPage from "../page/settings/SettingsPage";
import NewTestingPage from "../page/new-testing/NewTestingPage";
import LogoutPage from "../page/logout/LogoutPage";
import EditTestPage from "../page/admin/tests/edit/EditTestPage";
import EditTestingPage from "../page/admin/testings/edit/EditTestingPage";
import TestingPage from "../page/testing/TestingPage";
import ConfigurationPage from "../page/admin/configuration/ConfigurationPage";
import ResetPasswordPage from "../page/reset-password/ResetPasswordPage";
import {oauth2Provider} from "../../constant/oauth2.provider";
import OAuth2CallbackPage from "../page/oauth2-callback/OAuth2CallbackPage";
import AllAdminsPage from "../page/admin/all/AllAdminsPage/AllAdminsPage";
import TransferPage from "../page/admin/transfer/TransferPage";
import TransferDocumentationPage from "../page/admin/documentation/transfer/TransferDocumentationPage";

export const LayoutRoutes = () => {
    return (
        <>
            <Header/>

            <main className="s-vflex-center">
                <Routes>
                    <Route path={page.home} element={<HomePage/>}/>
                    <Route path={page.testing} element={<TestingPage/>}/>
                    <Route path={page.newTesting} element={<NewTestingPage/>}/>
                    <Route path={page.profile} element={<ProfilePage/>}/>
                    <Route path={page.login} element={<LoginPage/>}/>
                    <Route path={page.resetPassword} element={<ResetPasswordPage/>}/>
                    <Route path={page.oauth2Callback(oauth2Provider.google)}
                           element={<OAuth2CallbackPage providerName={oauth2Provider.google}/>}/>
                    <Route path={page.logout} element={<LogoutPage/>}/>
                    <Route path={page.register} element={<RegisterPage/>}/>
                    <Route path={page.settings} element={<SettingsPage/>}/>
                    <Route path={page.configuration} element={<ConfigurationPage/>}/>
                    <Route path={page.admin.all} element={<AllAdminsPage/>}/>
                    <Route path={page.admin.specializations.all} element={<AllSpecializationsPage/>}/>
                    <Route path={page.admin.subjects.all} element={<AllSubjectsPage/>}/>
                    <Route path={page.admin.tests.all} element={<AllTestsPage/>}/>
                    <Route path={page.admin.tests.create} element={<CreateTestPage/>}/>
                    <Route path={page.admin.tests.id().edit} element={<EditTestPage/>}/>
                    <Route path={page.admin.exams.all} element={<AllExamsPage/>}/>
                    <Route path={page.admin.testings.id().edit} element={<EditTestingPage/>}/>
                    <Route path={page.admin.transfer} element={<TransferPage />} />
                    <Route path={page.admin.documentation.transfer} element={<TransferDocumentationPage />} />
                </Routes>
            </main>

            <Footer />
        </>
    );
}
