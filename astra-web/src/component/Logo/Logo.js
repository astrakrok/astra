import { app } from '../../constant/app';
import { page } from '../../constant/page';
import './Logo.css';

export const Logo = () => {
    return (
        <a href={page.home} className="Logo s-hflex-start">
            <div className="flower">A</div>
            <div className="title s-vflex-center">{ app.name }</div>
        </a>
    );
}
