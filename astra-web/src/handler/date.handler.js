import moment from "moment"
import "moment-timezone";

export const convertToUserTimezone = (date, format = "YYYY-MM-DD HH:mm") => {
    return moment.utc(date).tz(moment.tz.guess()).format(format)
}
