const API_URL = `${(import.meta.env.VITE_API_URL || 'http://localhost:8081/api').replace(/\/$/, '')}/workers`;

async function request(path = '', options = {}) {
  const response = await fetch(`${API_URL}${path}`, { headers: { 'Content-Type': 'application/json' }, ...options });
  if (!response.ok) {
    const body = await response.json().catch(() => ({}));
    throw new Error(body.message || 'Unable to process the worker request.');
  }
  return response.status === 204 ? null : response.json();
}
export const registerWorker = (worker) => request('', { method: 'POST', body: JSON.stringify(worker) });
export const getWorker = (id) => request(`/${id}`);
export const updateWorker = (id, worker) => request(`/${id}`, { method: 'PUT', body: JSON.stringify(worker) });
export const deleteWorker = (id) => request(`/${id}`, { method: 'DELETE' });

export async function getWorkerQrCode(id) {
  const response = await fetch(`${API_URL}/${id}/qr`);
  if (!response.ok) {
    const body = await response.json().catch(() => ({}));
    throw new Error(body.message || 'Unable to load the QR code.');
  }
  return response.blob();
}
