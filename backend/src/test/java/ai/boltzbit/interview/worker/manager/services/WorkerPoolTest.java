package ai.boltzbit.interview.worker.manager.services;

import ai.boltzbit.interview.worker.manager.external.WorkerBroker;
import ai.boltzbit.interview.worker.manager.model.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static ai.boltzbit.interview.worker.manager.model.WorkerStatus.*;
import static ai.boltzbit.interview.worker.manager.model.WorkerType.CPU;
import static ai.boltzbit.interview.worker.manager.model.WorkerType.GPU;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class WorkerPoolTest {

    @InjectMocks
    private WorkerPool workerPool = new WorkerPool();

    @Mock
    private WorkerBroker workerBroker;

    @BeforeEach
    public void setup() {
        when(workerBroker.getWorkerList()).thenReturn(mockWorker());
        doNothing().when(workerBroker).requestStart(anyString());
    }

    @Test
    void processWorkerSuccess() throws Exception {

        Worker worker = workerPool.processWorkers(mockWorker());
        assertNotNull(worker);
        assertEquals(IDLE, worker.status);
    }

    @Test
    void processWorkerFailed() throws Exception {
        when(workerBroker.getWorkerList()).thenReturn(mockAllOutOfServiceWorkers());
        Worker worker = workerPool.processWorkers(mockAllOutOfServiceWorkers());
        assertNull(worker);
    }

    public List<Worker> mockAllOutOfServiceWorkers() {
        List<Worker> workerList = new ArrayList<>();
        workerList.add(new Worker("cpu-worker1", CPU, OUT_OF_SERVICE));
        workerList.add(new Worker("cpu-worker2", CPU, OUT_OF_SERVICE));
        workerList.add(new Worker("gpu-worker1", GPU, OUT_OF_SERVICE));
        workerList.add(new Worker("gpu-worker2", GPU, OUT_OF_SERVICE));

        return workerList;
    }

    public List<Worker> mockWorker() {
        List<Worker> workerList = new ArrayList<>();
        workerList.add(new Worker("cpu-worker1", CPU, IDLE));
        workerList.add(new Worker("cpu-worker2", CPU, BUSY));
        workerList.add(new Worker("gpu-worker1", GPU, IDLE));
        workerList.add(new Worker("gpu-worker2", GPU, OFF));

        return workerList;
    }
}