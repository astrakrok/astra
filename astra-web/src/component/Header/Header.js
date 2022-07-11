import { isGuest } from '../../handler/user.handler';
import AuthControl from '../AuthControl/AuthControl';
import Logo from '../Logo/Logo';
import SiteNavigation from "./SiteNavigation/SiteNavigation";
import ProfileActions from "./ProfileActions/ProfileActions";
import './Header.css';
import Spacer from '../Spacer/Spacer';

const Header = () => {
    return (
        <header className="full-width Header">
            <div className="container">
                <div className="row content">
                    <div className="s-hflex-start">
                        <Logo responsive="true" />
                        {
                            isGuest() ? (
                                <>
                                    <div className="equal-flex" />
                                    <AuthControl />
                                </>
                            ) : (
                                <>
                                    <SiteNavigation />
                                    <Spacer className="hide-on-small-only" width={40} />
                                    <ProfileActions />
                                </>
                            )
                        }
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
