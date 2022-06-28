import { Routes, Route } from "react-router-dom";
import { page } from "../../constant/page";
import { Footer } from "../Footer/Footer";
import { Header } from "../Header/Header";
import { HomePage } from "../page/home/HomePage";
import { LoginPage } from "../page/login/LoginPage";
import { RegisterPage } from "../page/register/RegisterPage";

export const LayoutRoutes = () => {
  return (
    <>
      <Header />
      
      <main className="s-vflex-center">
        <Routes>
          <Route path={page.home} element={<HomePage />} />
          <Route path={page.login} element={<LoginPage />} />
          <Route path={page.register} element={<RegisterPage />} />
        </Routes>
      </main>

      <Footer />
    </>
  );
}
