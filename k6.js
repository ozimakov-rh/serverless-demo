import {check} from 'k6';
import http from 'k6/http';

export const options = {
    stages: [
        { duration: '30s', target: 1   },
        { duration: '2m',  target: 200 },
        { duration: '2m',  target: 50  },
        { duration: '2m',  target: 300 },
        { duration: '1m',  target: 0   }
    ],
};

export default function () {
    const res = http.get(`${__ENV.PI_ENDPOINT}/pi/6`);
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}
