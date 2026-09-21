const BASE = `${(import.meta.env.VITE_API_URL || 'http://localhost:8081/api').replace(/\/$/, '')}/workers`;
async function request(path, options = {}) { const response = await fetch(`${BASE}${path}`, { headers: { 'Content-Type': 'application/json' }, ...options }); if (!response.ok) { const body = await response.json().catch(() => ({})); throw new Error(body.message || 'Unable to complete this health record request.'); } return response.status === 204 ? null : response.json(); }
export const listRecords = (workerId, type) => request(`/${workerId}/records?type=${type}`);
export const createRecord = (workerId, record) => request(`/${workerId}/records`, { method: 'POST', body: JSON.stringify(record) });
