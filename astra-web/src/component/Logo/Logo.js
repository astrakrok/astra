import { Link } from 'react-router-dom';
import { app } from '../../constant/app';
import { page } from '../../constant/page';
import './Logo.css';

export const Logo = ({
    responsive = false
}) => {
    return (
        <Link to={page.home} className="Logo s-hflex-start">
            <div className="flower">A</div>
            <div className={`title s-vflex-center${responsive ? " hide-on-small-only" : ""}`}>{ app.name }</div>
        </Link>
    );
}
