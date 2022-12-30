import TextHeader from "../../TextHeader/TextHeader";
import InfoText from "../../InfoText/InfoText";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import {getUser} from "../../../handler/user.handler";
import {useEffect, useState} from "react";
import {getAllStatistic} from "../../../service/users.exams.statistic.service";
import ExamStatisticItem from "../../ExamStatisticItem/ExamStatisticItem";
import withTitle from "../../hoc/withTitle/withTitle";
import "./ProfilePage.css";
import TabPanel from "../../TabPanel/TabPanel";

const ProfilePage = () => {
    const [statistic, setStatistic] = useState(null);

    useEffect(() => {
        const fetchStatistic = async () => {
            const result = await getAllStatistic();
            setStatistic(result);
        }

        fetchStatistic();
    }, []);

    const user = getUser();

    const renderStatisticItem = statistic => (
        <ExamStatisticItem key={statistic.id} statistic={statistic} />
    )

    const getTabs = () => {
        return [
            {
                title: "По іспитам"
            },
            {
                title: "По предметам"
            }
        ];
    }

    return (
        <div className="ProfilePage container">
            <div className="row">
                <div className="s-vflex">
                    <div className="s-hflex-center profile-information">
                        <div className="s-vflex">
                            <div className="full-name center">{user.name} {user.surname}</div>
                            <div className="email center">{user.email}</div>
                        </div>
                    </div>
                    <div className="statistic">
                        <TextHeader text="Статистика"/>
                        <div className="full-width">
                            <TabPanel tabs={getTabs()} stretch={true} />
                        </div>
                        <LoaderBoundary condition={statistic == null} className="s-hflex-center">
                            {
                                (statistic && statistic.length > 0) ? (
                                    statistic.map(renderStatisticItem)
                                ) : (
                                    <InfoText className="center">
                                        Ви не пройшли жодного іспиту
                                    </InfoText>
                                )
                            }
                        </LoaderBoundary>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default withTitle(ProfilePage, "Профіль");
