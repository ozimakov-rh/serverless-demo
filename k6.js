import { check } from 'k6';
import http from 'k6/http';

export const options = {
    stages: [
		{ duration: '3m',  target: 200 },
        { duration: '2m',  target: 200 },
        { duration: '1m',  target: 0 },
    ],
};

export default function () {
    const res = http.get(`${__ENV.PI_ENDPOINT}/pi/7`);
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}
